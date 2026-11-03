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
import com.example.data.AppStrings
import com.example.data.CurtainDataProvider
import com.example.model.*
import com.example.ui.components.MeasurementGuideDialog
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurtainCustomizerScreen(
    currentFabric: CurtainFabric,
    currentColor: CurtainColor,
    currentStyle: PleatStyle,
    currentHardware: CurtainHardware,
    includeSheer: Boolean,
    measurements: WindowMeasurements,
    appSettings: AppSettings,
    onFabricChange: (CurtainFabric, CurtainColor) -> Unit,
    onColorChange: (CurtainColor) -> Unit,
    onStyleChange: (PleatStyle) -> Unit,
    onHardwareChange: (CurtainHardware) -> Unit,
    onSheerToggle: (Boolean) -> Unit,
    onSaveDesign: (SavedCurtainDesign) -> Unit,
    onLaunchAR: () -> Unit
) {
    val context = LocalContext.current
    val strings = AppStrings.get(appSettings.language)

    // Popup measuring guide shown upon starting design process
    var showMeasurementGuide by remember { mutableStateOf(true) }

    var currentStep by remember { mutableIntStateOf(0) } // 0: Fabric, 1: Pleats, 2: Hardware
    val steps = listOf(strings.stepFabric, strings.stepPleats, strings.stepHardware)

    val quote = remember(currentFabric, currentStyle, currentHardware, includeSheer, measurements) {
        CurtainDataProvider.calculateQuote(
            fabric = currentFabric,
            measurements = measurements,
            pleatStyle = currentStyle,
            hardware = currentHardware,
            includeSheerLayer = includeSheer
        )
    }

    val currency = appSettings.currency
    val rate = currency.rateFromSar
    val currencySymbol = if (appSettings.language == AppLanguage.ARABIC) currency.symbolAr else currency.symbolEn

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = strings.customizerTitle,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { showMeasurementGuide = true }) {
                        Icon(
                            imageVector = Icons.Default.Straighten,
                            contentDescription = strings.measuringDialogTitle,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Button(
                        onClick = onLaunchAR,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(strings.previewInAR, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Steps Progress Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                steps.forEachIndexed { index, stepName ->
                    val isActive = index == currentStep
                    val isCompleted = index < currentStep
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { currentStep = index },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(
                                    when {
                                        isActive -> MaterialTheme.colorScheme.primary
                                        isCompleted -> MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                                        else -> MaterialTheme.colorScheme.surfaceVariant
                                    }
                                )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = stepName,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                            color = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Step Content Scrollable Area
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                when (currentStep) {
                    0 -> {
                        // Step 1: Fabric Selection & Colors
                        Text(
                            text = strings.selectFabric,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        // Fabric Types list
                        CurtainDataProvider.fabrics.forEach { fabric ->
                            val isSelected = fabric.id == currentFabric.id
                            Card(
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                                    else MaterialTheme.colorScheme.surface
                                ),
                                border = BorderStroke(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onFabricChange(fabric, fabric.colors.first())
                                    }
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = fabric.nameAr,
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "${String.format("%.1f", fabric.pricePerMeter * rate)} $currencySymbol / م",
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = fabric.textureDescription,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )

                                    Spacer(modifier = Modifier.height(6.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "${strings.opacity}: ${fabric.opacityPercent}%",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "• ${fabric.weightGsm} ${strings.gsm}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Color selection of chosen fabric
                        Text(
                            text = "${strings.selectColor} (${currentFabric.colors.size} ${if (appSettings.language == AppLanguage.ARABIC) "ألوان متوفرة" else "colors"})",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            currentFabric.colors.forEach { col ->
                                val isSelected = col.id == currentColor.id
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .clickable { onColorChange(col) }
                                        .padding(4.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(46.dp)
                                            .clip(CircleShape)
                                            .background(col.hexColor)
                                            .border(
                                                width = if (isSelected) 3.dp else 1.dp,
                                                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                                                shape = CircleShape
                                            )
                                    ) {
                                        if (isSelected) {
                                            Icon(
                                                Icons.Default.Check,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier
                                                    .size(20.dp)
                                                    .align(Alignment.Center)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = col.nameAr.split(" ")[0],
                                        style = MaterialTheme.typography.labelSmall,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }

                    1 -> {
                        // Step 2: Pleats & Styles
                        Text(
                            text = strings.pleatStyle,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        PleatStyle.values().forEach { style ->
                            val isSelected = style == currentStyle
                            Card(
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                                    else MaterialTheme.colorScheme.surface
                                ),
                                border = BorderStroke(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onStyleChange(style) }
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = style.nameAr,
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                        ) {
                                            Text(
                                                text = "x${style.fullnessRatio} كشكشة",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.primary,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = style.descriptionAr,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        // Sheer Dual Layer Toggle Card
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = CardDefaults.outlinedCardBorder(),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = strings.includeSheer,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = strings.includeSheerDesc,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Switch(
                                    checked = includeSheer,
                                    onCheckedChange = onSheerToggle
                                )
                            }
                        }
                    }

                    2 -> {
                        // Step 3: Hardware & Rods & Accessories
                        Text(
                            text = strings.hardwareRod,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        RodMaterial.values().forEach { rod ->
                            val isSelected = currentHardware.rodMaterial == rod
                            Card(
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                                    else MaterialTheme.colorScheme.surface
                                ),
                                border = BorderStroke(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onHardwareChange(currentHardware.copy(rodMaterial = rod))
                                    }
                            ) {
                                Row(
                                    modifier = Modifier.padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(CircleShape)
                                            .background(rod.color)
                                            .border(1.dp, Color.White, CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = rod.nameAr,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Finials
                        Text(
                            text = strings.finials,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FinialStyle.values().forEach { finial ->
                                val isSelected = currentHardware.finial == finial
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { onHardwareChange(currentHardware.copy(finial = finial)) },
                                    label = { Text(finial.nameAr) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Tiebacks
                        Text(
                            text = strings.tiebacks,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
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
                                    label = { Text(tie.nameAr) }
                                )
                            }
                        }

                        // Smart Motor Card
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = CardDefaults.outlinedCardBorder(),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = strings.smartMotor,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = strings.smartMotorDesc,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Switch(
                                    checked = currentHardware.isSmartMotorized,
                                    onCheckedChange = {
                                        onHardwareChange(currentHardware.copy(isSmartMotorized = it))
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Bottom Navigation & Save Action Bar
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (currentStep > 0) {
                        OutlinedButton(
                            onClick = { currentStep-- },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(strings.prevStep)
                        }
                    }

                    if (currentStep < steps.size - 1) {
                        Button(
                            onClick = { currentStep++ },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text(strings.nextStep, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Button(
                            onClick = {
                                val design = SavedCurtainDesign(
                                    id = UUID.randomUUID().toString(),
                                    title = "تصميم ستارة - ${currentFabric.nameAr.split(" ")[0]}",
                                    roomType = "غرفة معيشة",
                                    fabricId = currentFabric.id,
                                    fabricName = currentFabric.nameAr,
                                    colorId = currentColor.id,
                                    colorName = currentColor.nameAr,
                                    pleatStyle = currentStyle,
                                    hardware = currentHardware,
                                    measurements = measurements,
                                    fabricMetersNeeded = quote.mainFabricMeters,
                                    totalPriceSar = quote.totalPriceSar
                                )
                                onSaveDesign(design)
                                Toast.makeText(context, strings.snapshotSaved, Toast.LENGTH_LONG).show()
                            },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(Icons.Default.BookmarkBorder, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(strings.saveDesign, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    if (showMeasurementGuide) {
        MeasurementGuideDialog(
            language = appSettings.language,
            onDismiss = { showMeasurementGuide = false },
            onStartDesigning = { dontShowAgain ->
                showMeasurementGuide = false
            }
        )
    }
}
