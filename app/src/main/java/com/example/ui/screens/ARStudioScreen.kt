package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ar.CameraARView
import com.example.ar.CurtainARCanvas
import com.example.data.AppStrings
import com.example.data.CurtainDataProvider
import com.example.model.*
import com.example.ui.components.MeasurementGuideDialog
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ARStudioScreen(
    currentFabric: CurtainFabric,
    currentColor: CurtainColor,
    currentStyle: PleatStyle,
    currentHardware: CurtainHardware,
    includeSheer: Boolean,
    sheerColor: CurtainColor,
    appSettings: AppSettings,
    measurements: WindowMeasurements,
    onFabricChange: (CurtainFabric, CurtainColor) -> Unit,
    onColorChange: (CurtainColor) -> Unit,
    onStyleChange: (PleatStyle) -> Unit,
    onHardwareChange: (CurtainHardware) -> Unit,
    onSheerToggle: (Boolean) -> Unit,
    onSaveProject: (SavedCurtainDesign) -> Unit,
    onNavigateToCalculator: () -> Unit
) {
    val context = LocalContext.current
    val strings = AppStrings.get(appSettings.language)

    var isLiveCamera by remember { mutableStateOf(false) } // Default to photorealistic showcase room so emulator immediately looks stunning!
    var selectedRoomIndex by remember { mutableStateOf(0) }
    val rooms = CurtainDataProvider.roomPresets
    val activeRoom = rooms[selectedRoomIndex]

    var lighting by remember { mutableStateOf(LightingEnvironment.DAYLIGHT) }
    var openRatio by remember { mutableFloatStateOf(0.0f) } // 0 = closed, 1 = open
    var isAdjustingPins by remember { mutableStateOf(false) }
    var showGuideDialog by remember { mutableStateOf(false) }

    // Bottom Quick Panel Expand/Collapse State
    var isDrawerExpanded by remember { mutableStateOf(true) }
    var activePanelTab by remember { mutableStateOf(0) } // 0: Fabric & Colors, 1: Styles & Pleats, 2: Rod & Lighting

    Scaffold(
        topBar = {
            Surface(
                color = Color(0xCC1A1614),
                contentColor = Color.White,
                modifier = Modifier.statusBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Live Camera vs Sample Room Toggle
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFF2E2620))
                            .padding(2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = if (!isLiveCamera) MaterialTheme.colorScheme.primary else Color.Transparent,
                            modifier = Modifier.clickable { isLiveCamera = false }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.MeetingRoom, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.White)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(strings.sampleRoom, fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = if (isLiveCamera) MaterialTheme.colorScheme.primary else Color.Transparent,
                            modifier = Modifier.clickable { isLiveCamera = true }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Videocam, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.White)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(strings.liveCamera, fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    // Action Icons (Switch Room, Adjust Pins, Lighting)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (!isLiveCamera) {
                            IconButton(
                                onClick = {
                                    selectedRoomIndex = (selectedRoomIndex + 1) % rooms.size
                                }
                            ) {
                                Icon(Icons.Default.SwapHoriz, contentDescription = strings.switchRoom, tint = Color.White)
                            }
                        }

                        // Calibrate Pins toggle
                        IconButton(
                            onClick = { isAdjustingPins = !isAdjustingPins }
                        ) {
                            Icon(
                                imageVector = if (isAdjustingPins) Icons.Default.CheckCircle else Icons.Default.CropFree,
                                contentDescription = strings.adjustCorners,
                                tint = if (isAdjustingPins) MaterialTheme.colorScheme.primary else Color.White
                            )
                        }

                        // Guide Button
                        IconButton(onClick = { showGuideDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Straighten,
                                contentDescription = strings.measuringDialogTitle,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        // Lighting Cycle
                        IconButton(
                            onClick = {
                                lighting = when (lighting) {
                                    LightingEnvironment.DAYLIGHT -> LightingEnvironment.GOLDEN_HOUR
                                    LightingEnvironment.GOLDEN_HOUR -> LightingEnvironment.NIGHT_WARM
                                    LightingEnvironment.NIGHT_WARM -> LightingEnvironment.DAYLIGHT
                                }
                            }
                        ) {
                            Icon(
                                imageVector = when (lighting) {
                                    LightingEnvironment.DAYLIGHT -> Icons.Default.WbSunny
                                    LightingEnvironment.GOLDEN_HOUR -> Icons.Default.WbTwilight
                                    LightingEnvironment.NIGHT_WARM -> Icons.Default.Nightlight
                                },
                                contentDescription = strings.lighting,
                                tint = Color(0xFFFFD54F)
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Layer 1: Background (Camera feed or Sample room image)
            CameraARView(
                isLiveCamera = isLiveCamera,
                selectedRoom = activeRoom,
                lighting = lighting,
                language = appSettings.language,
                onSwitchToSampleRoom = { isLiveCamera = false }
            )

            // Layer 2: AR Canvas (Curtain rendering, folds, shadows, rods, tiebacks, draggable pins)
            CurtainARCanvas(
                fabric = currentFabric,
                color = currentColor,
                pleatStyle = currentStyle,
                hardware = currentHardware,
                lighting = lighting,
                openRatio = openRatio,
                includeSheerLayer = includeSheer,
                sheerColor = sheerColor,
                isAdjustingPins = isAdjustingPins,
                showGrid = appSettings.showARGrid,
                showMeasurements = appSettings.showDimensionOverlay,
                windowWidthCm = measurements.widthCm,
                windowHeightCm = measurements.heightCm,
                unit = appSettings.unit
            )

            // Layer 3: Pin adjustment helper banner
            AnimatedVisibility(
                visible = isAdjustingPins,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically(),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color(0xDD1E1A17),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.TouchApp, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = strings.dragToPosition,
                            color = Color.White,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Layer 4: Floating Right-Side Controls: Snap Photo & Open/Close Slider
            Column(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Snapshot button
                FloatingActionButton(
                    onClick = {
                        val breakdown = CurtainDataProvider.calculateQuote(
                            fabric = currentFabric,
                            measurements = measurements,
                            pleatStyle = currentStyle,
                            hardware = currentHardware,
                            includeSheerLayer = includeSheer
                        )
                        val newDesign = SavedCurtainDesign(
                            id = UUID.randomUUID().toString(),
                            title = "${activeRoom.nameAr} - ${currentFabric.nameAr.split(" ")[0]}",
                            roomType = activeRoom.type,
                            fabricId = currentFabric.id,
                            fabricName = currentFabric.nameAr,
                            colorId = currentColor.id,
                            colorName = currentColor.nameAr,
                            pleatStyle = currentStyle,
                            hardware = currentHardware,
                            measurements = measurements,
                            fabricMetersNeeded = breakdown.mainFabricMeters,
                            totalPriceSar = breakdown.totalPriceSar
                        )
                        onSaveProject(newDesign)
                        Toast.makeText(context, strings.snapshotSaved, Toast.LENGTH_LONG).show()
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                    modifier = Modifier.size(52.dp)
                ) {
                    Icon(Icons.Default.PhotoCamera, contentDescription = strings.captureSnapshot)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Curtains Open/Close Vertical Slider Pill
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color(0xCC1A1614),
                    modifier = Modifier
                        .width(44.dp)
                        .height(160.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            imageVector = Icons.Default.VerticalSplit,
                            contentDescription = strings.curtainOpen,
                            tint = if (openRatio > 0.7f) MaterialTheme.colorScheme.primary else Color.White,
                            modifier = Modifier
                                .size(18.dp)
                                .clickable { openRatio = 1.0f }
                        )

                        // Slider representation
                        Slider(
                            value = openRatio,
                            onValueChange = { openRatio = it },
                            modifier = Modifier
                                .weight(1f)
                                .width(100.dp)
                                // Rotate 270 degrees to make it vertical
                                .padding(0.dp),
                            colors = SliderDefaults.colors(
                                thumbColor = MaterialTheme.colorScheme.primary,
                                activeTrackColor = MaterialTheme.colorScheme.primary,
                                inactiveTrackColor = Color.White.copy(alpha = 0.3f)
                            )
                        )

                        Icon(
                            imageVector = Icons.Default.ViewAgenda,
                            contentDescription = strings.curtainClosed,
                            tint = if (openRatio < 0.2f) MaterialTheme.colorScheme.primary else Color.White,
                            modifier = Modifier
                                .size(18.dp)
                                .clickable { openRatio = 0.0f }
                        )
                    }
                }
            }

            // Layer 5: Bottom Studio Customizer Drawer
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                color = Color(0xF21C1815),
                tonalElevation = 8.dp,
                border = BorderStroke(1.dp, Color(0x33C59B4B))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    // Drawer Header / Handle
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isDrawerExpanded = !isDrawerExpanded },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(currentColor.hexColor)
                                    .border(1.dp, Color.White, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = currentFabric.nameAr,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1
                                )
                                Text(
                                    text = "${currentStyle.nameAr} • ${currentColor.nameAr}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFFC59B4B),
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TextButton(onClick = onNavigateToCalculator) {
                                Icon(Icons.Default.Calculate, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(strings.tabCalculator, color = MaterialTheme.colorScheme.primary, fontSize = 12.sp)
                            }

                            Icon(
                                imageVector = if (isDrawerExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }

                    AnimatedVisibility(visible = isDrawerExpanded) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Spacer(modifier = Modifier.height(10.dp))

                            // Tab selector: Fabric | Style | Hardware
                            TabRow(
                                selectedTabIndex = activePanelTab,
                                containerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.primary,
                                divider = {}
                            ) {
                                Tab(
                                    selected = activePanelTab == 0,
                                    onClick = { activePanelTab = 0 },
                                    text = { Text(strings.stepFabric, fontSize = 12.sp) }
                                )
                                Tab(
                                    selected = activePanelTab == 1,
                                    onClick = { activePanelTab = 1 },
                                    text = { Text(strings.stepPleats, fontSize = 12.sp) }
                                )
                                Tab(
                                    selected = activePanelTab == 2,
                                    onClick = { activePanelTab = 2 },
                                    text = { Text(strings.stepHardware, fontSize = 12.sp) }
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            when (activePanelTab) {
                                0 -> {
                                    // Fabric Selector Chips
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .horizontalScroll(rememberScrollState()),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        CurtainDataProvider.fabrics.forEach { f ->
                                            val isSelected = f.id == currentFabric.id
                                            FilterChip(
                                                selected = isSelected,
                                                onClick = {
                                                    onFabricChange(f, f.colors.first())
                                                },
                                                label = {
                                                    Text(
                                                        text = f.nameAr.split(" ")[0] + " (${f.opacityPercent}%)",
                                                        fontSize = 11.sp
                                                    )
                                                },
                                                colors = FilterChipDefaults.filterChipColors(
                                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                                    selectedLabelColor = Color.White,
                                                    containerColor = Color(0xFF2E2620),
                                                    labelColor = Color.White
                                                )
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    // Color Chips of selected fabric
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .horizontalScroll(rememberScrollState()),
                                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        currentFabric.colors.forEach { color ->
                                            val isSelected = color.id == currentColor.id
                                            Box(
                                                modifier = Modifier
                                                    .size(34.dp)
                                                    .clip(CircleShape)
                                                    .background(color.hexColor)
                                                    .border(
                                                        width = if (isSelected) 3.dp else 1.dp,
                                                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray,
                                                        shape = CircleShape
                                                    )
                                                    .clickable { onColorChange(color) }
                                            ) {
                                                if (isSelected) {
                                                    Icon(
                                                        Icons.Default.Check,
                                                        contentDescription = null,
                                                        modifier = Modifier
                                                            .size(16.dp)
                                                            .align(Alignment.Center),
                                                        tint = Color.White
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                1 -> {
                                    // Pleat / Heading Styles
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .horizontalScroll(rememberScrollState()),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        PleatStyle.values().forEach { style ->
                                            val isSelected = style == currentStyle
                                            OutlinedCard(
                                                onClick = { onStyleChange(style) },
                                                colors = CardDefaults.outlinedCardColors(
                                                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.25f) else Color(0xFF2E2620)
                                                ),
                                                border = BorderStroke(
                                                    if (isSelected) 2.dp else 1.dp,
                                                    if (isSelected) MaterialTheme.colorScheme.primary else Color(0x33FFFFFF)
                                                ),
                                                modifier = Modifier.padding(vertical = 4.dp)
                                            ) {
                                                Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
                                                    Text(
                                                        text = style.nameAr.split("(")[0].trim(),
                                                        style = MaterialTheme.typography.labelMedium,
                                                        color = Color.White,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                    Text(
                                                        text = "x${style.fullnessRatio} كشكشة",
                                                        style = MaterialTheme.typography.labelSmall,
                                                        color = MaterialTheme.colorScheme.primary,
                                                        fontSize = 10.sp
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    // Sheer Dual-layer Toggle
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = strings.includeSheer,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.White
                                        )
                                        Switch(
                                            checked = includeSheer,
                                            onCheckedChange = onSheerToggle,
                                            colors = SwitchDefaults.colors(
                                                checkedThumbColor = MaterialTheme.colorScheme.primary,
                                                checkedTrackColor = MaterialTheme.colorScheme.primaryContainer
                                            )
                                        )
                                    }
                                }
                                2 -> {
                                    // Rod Material & Accessories
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .horizontalScroll(rememberScrollState()),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        RodMaterial.values().forEach { rod ->
                                            val isSelected = currentHardware.rodMaterial == rod
                                            FilterChip(
                                                selected = isSelected,
                                                onClick = { onHardwareChange(currentHardware.copy(rodMaterial = rod)) },
                                                label = { Text(rod.nameAr, fontSize = 11.sp) },
                                                colors = FilterChipDefaults.filterChipColors(
                                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                                    selectedLabelColor = Color.White,
                                                    containerColor = Color(0xFF2E2620),
                                                    labelColor = Color.White
                                                )
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))

                                    // Tieback Selector
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .horizontalScroll(rememberScrollState()),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        TiebackStyle.values().forEach { tie ->
                                            val isSelected = currentHardware.tieback == tie
                                            FilterChip(
                                                selected = isSelected,
                                                onClick = { onHardwareChange(currentHardware.copy(tieback = tie)) },
                                                label = { Text(tie.nameAr, fontSize = 11.sp) },
                                                colors = FilterChipDefaults.filterChipColors(
                                                    selectedContainerColor = Color(0xFFC59B4B),
                                                    selectedLabelColor = Color.White,
                                                    containerColor = Color(0xFF2E2620),
                                                    labelColor = Color.White
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showGuideDialog) {
        MeasurementGuideDialog(
            language = appSettings.language,
            onDismiss = { showGuideDialog = false },
            onStartDesigning = { dontShowAgain ->
                showGuideDialog = false
            }
        )
    }
}
