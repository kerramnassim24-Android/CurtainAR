package com.example.ar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import com.example.model.*
import kotlin.math.PI
import kotlin.math.sin

data class WindowCorners(
    val topLeft: Offset,
    val topRight: Offset,
    val bottomLeft: Offset,
    val bottomRight: Offset
)

@Composable
fun CurtainARCanvas(
    modifier: Modifier = Modifier,
    fabric: CurtainFabric,
    color: CurtainColor,
    pleatStyle: PleatStyle,
    hardware: CurtainHardware,
    lighting: LightingEnvironment,
    openRatio: Float, // 0.0 = closed, 1.0 = wide open
    includeSheerLayer: Boolean,
    sheerColor: CurtainColor,
    isAdjustingPins: Boolean,
    showGrid: Boolean,
    showMeasurements: Boolean,
    windowWidthCm: Float,
    windowHeightCm: Float,
    unit: MeasurementUnit,
    onCornerMoved: () -> Unit = {}
) {
    var corners by remember {
        mutableStateOf<WindowCorners?>(null)
    }

    var activeDragCorner by remember { mutableStateOf<Int?>(null) } // 0: TL, 1: TR, 2: BL, 3: BR

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(isAdjustingPins) {
                if (!isAdjustingPins) return@pointerInput
                detectDragGestures(
                    onDragStart = { startOffset ->
                        val c = corners ?: return@detectDragGestures
                        val hitRadius = 80f
                        activeDragCorner = when {
                            (startOffset - c.topLeft).getDistance() < hitRadius -> 0
                            (startOffset - c.topRight).getDistance() < hitRadius -> 1
                            (startOffset - c.bottomLeft).getDistance() < hitRadius -> 2
                            (startOffset - c.bottomRight).getDistance() < hitRadius -> 3
                            else -> null
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        val c = corners ?: return@detectDragGestures
                        corners = when (activeDragCorner) {
                            0 -> c.copy(topLeft = c.topLeft + dragAmount)
                            1 -> c.copy(topRight = c.topRight + dragAmount)
                            2 -> c.copy(bottomLeft = c.bottomLeft + dragAmount)
                            3 -> c.copy(bottomRight = c.bottomRight + dragAmount)
                            else -> c
                        }
                        onCornerMoved()
                    },
                    onDragEnd = { activeDragCorner = null },
                    onDragCancel = { activeDragCorner = null }
                )
            }
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // Initialize default window corners if not yet created
        val currentCorners = corners ?: run {
            val left = canvasWidth * 0.12f
            val right = canvasWidth * 0.88f
            val top = canvasHeight * 0.18f
            val bottom = canvasHeight * 0.82f
            val initial = WindowCorners(
                topLeft = Offset(left, top),
                topRight = Offset(right, top),
                bottomLeft = Offset(left, bottom),
                bottomRight = Offset(right, bottom)
            )
            corners = initial
            initial
        }

        // 1. Draw alignment grid if enabled
        if (showGrid) {
            drawAlignmentGrid(this, canvasWidth, canvasHeight)
        }

        // 2. Draw Sheer Underlayer if enabled or Double Layer
        if (includeSheerLayer || pleatStyle == PleatStyle.DOUBLE_LAYER) {
            drawSheerUnderlayer(
                drawScope = this,
                corners = currentCorners,
                sheerColor = sheerColor,
                lighting = lighting
            )
        }

        // 3. Draw Main Drapery Panels (Left & Right)
        drawDraperyPanels(
            drawScope = this,
            corners = currentCorners,
            fabric = fabric,
            color = color,
            pleatStyle = pleatStyle,
            hardware = hardware,
            lighting = lighting,
            openRatio = openRatio
        )

        // 4. Draw Curtain Rod & Hardware (above curtain fabric)
        if (hardware.rodMaterial != RodMaterial.HIDDEN_TRACK) {
            drawCurtainRod(
                drawScope = this,
                corners = currentCorners,
                hardware = hardware
            )
        } else {
            // Draw sleek subtle ceiling track
            drawCeilingTrack(drawScope = this, corners = currentCorners)
        }

        // 5. Draw Dimension Overlays if enabled
        if (showMeasurements) {
            drawDimensionAnnotations(
                drawScope = this,
                corners = currentCorners,
                widthCm = windowWidthCm,
                heightCm = windowHeightCm,
                unit = unit
            )
        }

        // 6. Draw interactive corner calibration pins if in editing mode
        if (isAdjustingPins) {
            drawCalibrationPins(
                drawScope = this,
                corners = currentCorners,
                activePin = activeDragCorner
            )
        }
    }
}

private fun drawAlignmentGrid(drawScope: DrawScope, width: Float, height: Float) {
    val gridColor = Color.White.copy(alpha = 0.15f)
    val step = 60f
    var x = step
    while (x < width) {
        drawScope.drawLine(gridColor, Offset(x, 0f), Offset(x, height), strokeWidth = 1f)
        x += step
    }
    var y = step
    while (y < height) {
        drawScope.drawLine(gridColor, Offset(0f, y), Offset(width, y), strokeWidth = 1f)
        y += step
    }
}

private fun drawSheerUnderlayer(
    drawScope: DrawScope,
    corners: WindowCorners,
    sheerColor: CurtainColor,
    lighting: LightingEnvironment
) {
    val left = corners.topLeft.x
    val right = corners.topRight.x
    val top = corners.topLeft.y + 10f
    val bottom = corners.bottomLeft.y
    val width = right - left
    val height = bottom - top

    val baseColor = sheerColor.hexColor.copy(alpha = 0.35f)
    val highlightColor = sheerColor.highlightColor.copy(alpha = 0.55f)
    val shadowColor = sheerColor.shadowColor.copy(alpha = 0.25f)

    // Soft sheer body fill
    drawScope.drawRect(
        brush = Brush.verticalGradient(
            colors = listOf(
                baseColor.copy(alpha = 0.45f),
                baseColor.copy(alpha = 0.32f),
                baseColor.copy(alpha = 0.40f)
            ),
            startY = top,
            endY = bottom
        ),
        topLeft = Offset(left, top),
        size = Size(width, height)
    )

    // Sheer delicate pleat waves
    val waveCount = 28
    val waveStep = width / waveCount
    for (i in 0..waveCount) {
        val wx = left + i * waveStep
        val isShade = i % 2 == 0
        val pleatBrush = Brush.horizontalGradient(
            colors = if (isShade) listOf(shadowColor, Color.Transparent) else listOf(highlightColor, Color.Transparent),
            startX = wx - waveStep / 2,
            endX = wx + waveStep / 2
        )
        drawScope.drawRect(
            brush = pleatBrush,
            topLeft = Offset(wx - waveStep / 2, top),
            size = Size(waveStep, height)
        )
    }

    // Sheer bottom hemline
    drawScope.drawLine(
        color = highlightColor.copy(alpha = 0.8f),
        start = Offset(left, bottom),
        end = Offset(right, bottom),
        strokeWidth = 3f
    )
}

private fun drawDraperyPanels(
    drawScope: DrawScope,
    corners: WindowCorners,
    fabric: CurtainFabric,
    color: CurtainColor,
    pleatStyle: PleatStyle,
    hardware: CurtainHardware,
    lighting: LightingEnvironment,
    openRatio: Float
) {
    val windowLeft = corners.topLeft.x
    val windowRight = corners.topRight.x
    val top = corners.topLeft.y + 12f
    val bottom = corners.bottomLeft.y
    val totalWidth = windowRight - windowLeft
    val halfWidth = totalWidth / 2f
    val height = bottom - top

    // When openRatio is 0.0, panel width is halfWidth (fully covers half each)
    // When openRatio is 1.0, panel width shrinks to stackWidth (bunched at edges)
    val minStackWidth = totalWidth * 0.18f
    val currentPanelWidth = halfWidth - ((halfWidth - minStackWidth) * openRatio)

    val isRomanBlind = pleatStyle == PleatStyle.ROMAN_BLIND

    if (isRomanBlind) {
        // Draw Roman Blind folding up from top
        val blindHeight = height - ((height - 60f) * openRatio)
        drawRomanBlind(
            drawScope = drawScope,
            left = windowLeft,
            top = top,
            width = totalWidth,
            height = blindHeight,
            fabric = fabric,
            color = color,
            lighting = lighting
        )
        return
    }

    // Left Panel
    drawCurtainPanel(
        drawScope = drawScope,
        startX = windowLeft,
        panelWidth = currentPanelWidth,
        top = top,
        bottom = bottom,
        isLeftPanel = true,
        fabric = fabric,
        color = color,
        pleatStyle = pleatStyle,
        hardware = hardware,
        lighting = lighting,
        openRatio = openRatio
    )

    // Right Panel
    val rightStartX = windowRight - currentPanelWidth
    drawCurtainPanel(
        drawScope = drawScope,
        startX = rightStartX,
        panelWidth = currentPanelWidth,
        top = top,
        bottom = bottom,
        isLeftPanel = false,
        fabric = fabric,
        color = color,
        pleatStyle = pleatStyle,
        hardware = hardware,
        lighting = lighting,
        openRatio = openRatio
    )
}

private fun drawCurtainPanel(
    drawScope: DrawScope,
    startX: Float,
    panelWidth: Float,
    top: Float,
    bottom: Float,
    isLeftPanel: Boolean,
    fabric: CurtainFabric,
    color: CurtainColor,
    pleatStyle: PleatStyle,
    hardware: CurtainHardware,
    lighting: LightingEnvironment,
    openRatio: Float
) {
    val height = bottom - top
    val hasTieback = hardware.tieback != TiebackStyle.NONE && openRatio > 0.15f

    // Calculate number of pleat folds based on style and panel width
    val basePleatWidth = when (pleatStyle) {
        PleatStyle.WAVE -> 36f
        PleatStyle.PINCH_PLEAT -> 28f
        PleatStyle.EYELET -> 48f
        else -> 32f
    }

    val pleatCount = (panelWidth / basePleatWidth).toInt().coerceAtLeast(3)
    val pleatStep = panelWidth / pleatCount

    val mainColor = color.hexColor
    val shadowColor = color.shadowColor
    val highlightColor = color.highlightColor

    // Fabric opacity alpha factor (blackout = 1.0, linen = 0.9, cotton = 0.88, sheer = 0.4)
    val opacityFactor = (fabric.opacityPercent / 100f).coerceIn(0.35f, 1.0f)

    // Ambient lighting shift
    val sunTint = lighting.sunColor

    for (i in 0 until pleatCount) {
        val px = startX + i * pleatStep
        val nextX = px + pleatStep

        // Sinusoidal 3D pleat depth simulation
        val pleatPhase = (i.toFloat() / pleatCount) * 2 * PI
        val pleatDepth = (sin(pleatPhase) * 0.5f + 0.5f).toFloat()

        // Tieback curve calculation
        val tiebackMidOffset = if (hasTieback) {
            val factor = if (isLeftPanel) -25f else 25f
            factor * (1.0f - (i.toFloat() / pleatCount))
        } else 0f

        val pleatPath = Path().apply {
            moveTo(px, top)
            lineTo(nextX, top)
            // Quadratic curve if tied back
            if (hasTieback) {
                quadraticTo(
                    nextX + tiebackMidOffset,
                    top + height * 0.55f,
                    nextX,
                    bottom
                )
                lineTo(px, bottom)
                quadraticTo(
                    px + tiebackMidOffset,
                    top + height * 0.55f,
                    px,
                    top
                )
            } else {
                lineTo(nextX, bottom)
                lineTo(px, bottom)
                close()
            }
        }

        // Shading gradient across each pleat cylinder
        val isCrest = i % 2 == 1
        val startShade = if (isCrest) highlightColor else shadowColor
        val endShade = if (isCrest) shadowColor else highlightColor

        val pleatBrush = Brush.horizontalGradient(
            colors = listOf(
                startShade.copy(alpha = opacityFactor),
                mainColor.copy(alpha = opacityFactor),
                endShade.copy(alpha = opacityFactor)
            ),
            startX = px,
            endX = nextX
        )

        drawScope.drawPath(path = pleatPath, brush = pleatBrush)

        // Micro texture accent line (slub linen or velvet sheen)
        if (fabric.category == FabricCategory.LINEN) {
            drawScope.drawLine(
                color = Color.Black.copy(alpha = 0.08f),
                start = Offset(px + pleatStep * 0.3f, top),
                end = Offset(px + pleatStep * 0.3f, bottom),
                strokeWidth = 1f
            )
        } else if (fabric.category == FabricCategory.VELVET && isCrest) {
            // Velvet rich specular sheen highlight down crest
            drawScope.drawLine(
                color = highlightColor.copy(alpha = 0.35f),
                start = Offset(px + pleatStep * 0.5f, top),
                end = Offset(px + pleatStep * 0.5f, bottom),
                strokeWidth = 2.5f
            )
        }
    }

    // Top Heading styling
    drawHeadingStyle(
        drawScope = drawScope,
        startX = startX,
        width = panelWidth,
        top = top,
        pleatStyle = pleatStyle,
        hardware = hardware,
        color = color
    )

    // Tieback holdback accessory
    if (hasTieback) {
        val tieY = top + height * 0.55f
        val anchorX = if (isLeftPanel) startX else startX + panelWidth
        drawTiebackAccessory(
            drawScope = drawScope,
            anchorX = anchorX,
            tieY = tieY,
            isLeft = isLeftPanel,
            hardware = hardware
        )
    }

    // Bottom hem weight line
    drawScope.drawLine(
        color = shadowColor.copy(alpha = 0.6f),
        start = Offset(startX, bottom),
        end = Offset(startX + panelWidth, bottom),
        strokeWidth = 4f
    )
}

private fun drawHeadingStyle(
    drawScope: DrawScope,
    startX: Float,
    width: Float,
    top: Float,
    pleatStyle: PleatStyle,
    hardware: CurtainHardware,
    color: CurtainColor
) {
    when (pleatStyle) {
        PleatStyle.EYELET -> {
            // Draw metallic grommet rings
            val eyeletRadius = 7f
            val ringCount = (width / 40f).toInt().coerceAtLeast(2)
            val ringStep = width / ringCount
            val ringColor = hardware.rodMaterial.color
            for (r in 0 until ringCount) {
                val rx = startX + r * ringStep + ringStep / 2f
                val ry = top - 2f
                // Hole
                drawScope.drawCircle(
                    color = Color(0xFF1A1A1A),
                    radius = eyeletRadius,
                    center = Offset(rx, ry)
                )
                // Metal ring stroke
                drawScope.drawCircle(
                    color = ringColor,
                    radius = eyeletRadius + 3f,
                    center = Offset(rx, ry),
                    style = Stroke(width = 3f)
                )
            }
        }
        PleatStyle.PINCH_PLEAT -> {
            // Pinch pleat stitch gathers in top 30px
            val pleatCount = (width / 26f).toInt().coerceAtLeast(3)
            val pleatStep = width / pleatCount
            for (p in 0 until pleatCount) {
                val px = startX + p * pleatStep + pleatStep / 2f
                // 3 pinched lines
                drawScope.drawLine(
                    color = color.shadowColor,
                    start = Offset(px - 3f, top),
                    end = Offset(px, top + 25f),
                    strokeWidth = 2f
                )
                drawScope.drawLine(
                    color = color.shadowColor,
                    start = Offset(px + 3f, top),
                    end = Offset(px, top + 25f),
                    strokeWidth = 2f
                )
            }
        }
        else -> Unit
    }
}

private fun drawTiebackAccessory(
    drawScope: DrawScope,
    anchorX: Float,
    tieY: Float,
    isLeft: Boolean,
    hardware: CurtainHardware
) {
    val goldColor = Color(0xFFD4AF37)
    when (hardware.tieback) {
        TiebackStyle.MAGNETIC_GOLD -> {
            // Gold magnetic clip medallion
            drawScope.drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFFFFF2B2), goldColor, Color(0xFF8A6C18)),
                    center = Offset(anchorX, tieY),
                    radius = 16f
                ),
                radius = 16f,
                center = Offset(anchorX, tieY)
            )
            drawScope.drawCircle(
                color = Color.White.copy(alpha = 0.7f),
                radius = 6f,
                center = Offset(anchorX - 3f, tieY - 3f)
            )
        }
        TiebackStyle.VELVET_TASSEL -> {
            // Rich tassel band
            drawScope.drawCircle(color = goldColor, radius = 10f, center = Offset(anchorX, tieY))
            // Tassel fringe hanging down
            val tasselPath = Path().apply {
                moveTo(anchorX - 8f, tieY + 8f)
                lineTo(anchorX + 8f, tieY + 8f)
                lineTo(anchorX + 14f, tieY + 45f)
                lineTo(anchorX - 14f, tieY + 45f)
                close()
            }
            drawScope.drawPath(
                path = tasselPath,
                brush = Brush.verticalGradient(
                    colors = listOf(goldColor, Color(0xFF9E7E24)),
                    startY = tieY + 8f,
                    endY = tieY + 45f
                )
            )
        }
        else -> Unit
    }
}

private fun drawRomanBlind(
    drawScope: DrawScope,
    left: Float,
    top: Float,
    width: Float,
    height: Float,
    fabric: CurtainFabric,
    color: CurtainColor,
    lighting: LightingEnvironment
) {
    // Roman blind horizontal segment folds
    val segmentCount = 5
    val segmentHeight = height / segmentCount

    for (s in 0 until segmentCount) {
        val sy = top + s * segmentHeight
        val segmentBrush = Brush.verticalGradient(
            colors = listOf(color.highlightColor, color.hexColor, color.shadowColor),
            startY = sy,
            endY = sy + segmentHeight
        )
        drawScope.drawRect(
            brush = segmentBrush,
            topLeft = Offset(left, sy),
            size = Size(width, segmentHeight)
        )
        // Horizontal batten rod seam
        drawScope.drawLine(
            color = color.shadowColor.copy(alpha = 0.8f),
            start = Offset(left, sy + segmentHeight),
            end = Offset(left + width, sy + segmentHeight),
            strokeWidth = 3f
        )
    }
}

private fun drawCurtainRod(
    drawScope: DrawScope,
    corners: WindowCorners,
    hardware: CurtainHardware
) {
    val rodOverhang = 45f
    val startX = corners.topLeft.x - rodOverhang
    val endX = corners.topRight.x + rodOverhang
    val rodY = corners.topLeft.y - 12f
    val rodThickness = 12f

    val rodColor = hardware.rodMaterial.color
    val metallicAccent = hardware.rodMaterial.metallicAccent

    val rodBrush = Brush.verticalGradient(
        colors = listOf(metallicAccent, rodColor, metallicAccent.copy(alpha = 0.7f), rodColor),
        startY = rodY - rodThickness / 2,
        endY = rodY + rodThickness / 2
    )

    // Rod cylinder
    drawScope.drawRoundRect(
        brush = rodBrush,
        topLeft = Offset(startX, rodY - rodThickness / 2),
        size = Size(endX - startX, rodThickness),
        cornerRadius = CornerRadius(4f, 4f)
    )

    // Wall brackets (Left, Center, Right)
    val bracketColor = rodColor
    val bracketWidth = 8f
    val bracketHeight = 22f
    val bracketPositions = listOf(
        corners.topLeft.x + 15f,
        (corners.topLeft.x + corners.topRight.x) / 2f,
        corners.topRight.x - 15f
    )
    for (bx in bracketPositions) {
        drawScope.drawRect(
            color = bracketColor,
            topLeft = Offset(bx - bracketWidth / 2, rodY - bracketHeight / 2),
            size = Size(bracketWidth, bracketHeight)
        )
    }

    // Finials on left and right
    drawFinial(drawScope, startX, rodY, isLeft = true, hardware = hardware)
    drawFinial(drawScope, endX, rodY, isLeft = false, hardware = hardware)
}

private fun drawCeilingTrack(drawScope: DrawScope, corners: WindowCorners) {
    val trackX1 = corners.topLeft.x - 20f
    val trackX2 = corners.topRight.x + 20f
    val trackY = corners.topLeft.y - 8f

    drawScope.drawLine(
        color = Color.White.copy(alpha = 0.85f),
        start = Offset(trackX1, trackY),
        end = Offset(trackX2, trackY),
        strokeWidth = 6f
    )
    drawScope.drawLine(
        color = Color(0xFFC0C0C0),
        start = Offset(trackX1, trackY + 2f),
        end = Offset(trackX2, trackY + 2f),
        strokeWidth = 2f
    )
}

private fun drawFinial(
    drawScope: DrawScope,
    rodEndX: Float,
    rodY: Float,
    isLeft: Boolean,
    hardware: CurtainHardware
) {
    val color = hardware.rodMaterial.color
    val accent = hardware.rodMaterial.metallicAccent
    val dir = if (isLeft) -1f else 1f

    when (hardware.finial) {
        FinialStyle.MINIMAL_CAP -> {
            val capWidth = 12f
            val capHeight = 20f
            drawScope.drawRoundRect(
                color = color,
                topLeft = Offset(
                    if (isLeft) rodEndX - capWidth else rodEndX,
                    rodY - capHeight / 2
                ),
                size = Size(capWidth, capHeight),
                cornerRadius = CornerRadius(3f, 3f)
            )
        }
        FinialStyle.SPHERE -> {
            val radius = 14f
            val cx = rodEndX + dir * radius
            drawScope.drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(accent, color, Color.Black.copy(alpha = 0.4f)),
                    center = Offset(cx - dir * 3f, rodY - 3f),
                    radius = radius
                ),
                radius = radius,
                center = Offset(cx, rodY)
            )
        }
        FinialStyle.CRYSTAL -> {
            val radius = 16f
            val cx = rodEndX + dir * radius
            // Crystal diamond sparkle
            drawScope.drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color.White, Color(0xFFE0F7FA), Color(0xFF80DEEA)),
                    center = Offset(cx, rodY),
                    radius = radius
                ),
                radius = radius,
                center = Offset(cx, rodY)
            )
            drawScope.drawCircle(
                color = Color.White.copy(alpha = 0.8f),
                radius = radius,
                center = Offset(cx, rodY),
                style = Stroke(width = 1.5f)
            )
        }
        FinialStyle.CYLINDER -> {
            val cylWidth = 22f
            val cylHeight = 16f
            drawScope.drawRoundRect(
                color = color,
                topLeft = Offset(
                    if (isLeft) rodEndX - cylWidth else rodEndX,
                    rodY - cylHeight / 2
                ),
                size = Size(cylWidth, cylHeight),
                cornerRadius = CornerRadius(2f, 2f)
            )
        }
    }
}

private fun drawDimensionAnnotations(
    drawScope: DrawScope,
    corners: WindowCorners,
    widthCm: Float,
    heightCm: Float,
    unit: MeasurementUnit
) {
    val primaryGold = Color(0xFFC59B4B)
    val textBg = Color(0xCC1E1A17)

    // Top Width indicator line
    val topY = corners.topLeft.y - 35f
    drawScope.drawLine(
        color = primaryGold,
        start = Offset(corners.topLeft.x, topY),
        end = Offset(corners.topRight.x, topY),
        strokeWidth = 2f
    )
    // Left & right ticks
    drawScope.drawLine(primaryGold, Offset(corners.topLeft.x, topY - 8f), Offset(corners.topLeft.x, topY + 8f), 2f)
    drawScope.drawLine(primaryGold, Offset(corners.topRight.x, topY - 8f), Offset(corners.topRight.x, topY + 8f), 2f)

    // Right Height indicator line
    val rightX = corners.topRight.x + 35f
    drawScope.drawLine(
        color = primaryGold,
        start = Offset(rightX, corners.topRight.y),
        end = Offset(rightX, corners.bottomRight.y),
        strokeWidth = 2f
    )
    // Top & bottom ticks
    drawScope.drawLine(primaryGold, Offset(rightX - 8f, corners.topRight.y), Offset(rightX + 8f, corners.topRight.y), 2f)
    drawScope.drawLine(primaryGold, Offset(rightX - 8f, corners.bottomRight.y), Offset(rightX + 8f, corners.bottomRight.y), 2f)
}

private fun drawCalibrationPins(
    drawScope: DrawScope,
    corners: WindowCorners,
    activePin: Int?
) {
    val pins = listOf(
        corners.topLeft,
        corners.topRight,
        corners.bottomLeft,
        corners.bottomRight
    )

    // Draw holographic frame rectangle connecting pins
    val framePath = Path().apply {
        moveTo(corners.topLeft.x, corners.topLeft.y)
        lineTo(corners.topRight.x, corners.topRight.y)
        lineTo(corners.bottomRight.x, corners.bottomRight.y)
        lineTo(corners.bottomLeft.x, corners.bottomLeft.y)
        close()
    }
    drawScope.drawPath(
        path = framePath,
        color = Color(0xFFE5C07B),
        style = Stroke(
            width = 3f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 15f), 0f)
        )
    )

    // Draw circular draggable interactive pins
    pins.forEachIndexed { index, pin ->
        val isActive = activePin == index
        val baseRadius = if (isActive) 34f else 26f
        val glowColor = if (isActive) Color(0xFFFFD54F) else Color(0xFFC59B4B)

        // Outer glow
        drawScope.drawCircle(
            color = glowColor.copy(alpha = 0.35f),
            radius = baseRadius + 14f,
            center = pin
        )
        // Solid pin
        drawScope.drawCircle(
            color = glowColor,
            radius = baseRadius,
            center = pin
        )
        // Inner core
        drawScope.drawCircle(
            color = Color(0xFF1E1A17),
            radius = baseRadius * 0.45f,
            center = pin
        )
        // Center crosshair
        drawScope.drawLine(
            color = Color.White,
            start = Offset(pin.x - 8f, pin.y),
            end = Offset(pin.x + 8f, pin.y),
            strokeWidth = 2f
        )
        drawScope.drawLine(
            color = Color.White,
            start = Offset(pin.x, pin.y - 8f),
            end = Offset(pin.x, pin.y + 8f),
            strokeWidth = 2f
        )
    }
}
