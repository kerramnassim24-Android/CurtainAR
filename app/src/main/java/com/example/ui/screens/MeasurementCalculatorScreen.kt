package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppStrings
import com.example.data.CurtainDataProvider
import com.example.model.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeasurementCalculatorScreen(
    currentFabric: CurtainFabric,
    currentColor: CurtainColor,
    currentStyle: PleatStyle,
    currentHardware: CurtainHardware,
    includeSheer: Boolean,
    measurements: WindowMeasurements,
    appSettings: AppSettings,
    onMeasurementsChange: (WindowMeasurements) -> Unit,
    onNavigateToAR: () -> Unit
) {
    val context = LocalContext.current
    val strings = AppStrings.get(appSettings.language)

    var widthCm by remember { mutableFloatStateOf(measurements.widthCm) }
    var heightCm by remember { mutableFloatStateOf(measurements.heightCm) }
    var extensionCm by remember { mutableFloatStateOf(measurements.extensionCm) }
    var fullness by remember { mutableFloatStateOf(currentStyle.fullnessRatio) }
    var includeInstallation by remember { mutableStateOf(true) }
    var showTipsDialog by remember { mutableStateOf(false) }

    val currentMeasurements = remember(widthCm, heightCm, extensionCm, fullness) {
        WindowMeasurements(
            widthCm = widthCm,
            heightCm = heightCm,
            extensionCm = extensionCm,
            fullnessFactor = fullness
        )
    }

    LaunchedEffect(currentMeasurements) {
        onMeasurementsChange(currentMeasurements)
    }

    val quote = remember(currentFabric, currentMeasurements, currentStyle, currentHardware, includeSheer, includeInstallation) {
        CurtainDataProvider.calculateQuote(
            fabric = currentFabric,
            measurements = currentMeasurements,
            pleatStyle = currentStyle,
            hardware = currentHardware,
            includeSheerLayer = includeSheer,
            includeInstallation = includeInstallation
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
                        text = strings.calculatorTitle,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { showTipsDialog = true }) {
                        Icon(Icons.Default.HelpOutline, contentDescription = strings.expertTips, tint = MaterialTheme.colorScheme.primary)
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Active Selection Preview Banner
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = currentFabric.nameAr,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${currentStyle.nameAr} • ${currentHardware.rodMaterial.nameAr}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Button(
                        onClick = onNavigateToAR,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(strings.previewInAR, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Section 1: Window Dimensions Input
            Text(
                text = strings.windowMeasurements,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Width Slider
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = strings.windowWidth, style = MaterialTheme.typography.bodyMedium)
                        Text(
                            text = "${widthCm.toInt()} ${appSettings.unit.labelAr}",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Slider(
                        value = widthCm,
                        onValueChange = { widthCm = it },
                        valueRange = 80f..500f,
                        steps = 41
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Height Slider
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = strings.windowHeight, style = MaterialTheme.typography.bodyMedium)
                        Text(
                            text = "${heightCm.toInt()} ${appSettings.unit.labelAr}",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Slider(
                        value = heightCm,
                        onValueChange = { heightCm = it },
                        valueRange = 120f..380f,
                        steps = 25
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Rod Extension
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = strings.wallExtension, style = MaterialTheme.typography.bodyMedium)
                        Text(
                            text = "${extensionCm.toInt()} ${appSettings.unit.labelAr}",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Slider(
                        value = extensionCm,
                        onValueChange = { extensionCm = it },
                        valueRange = 10f..50f,
                        steps = 7
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Section 2: Fabric Requirement Banner
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Straighten,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = strings.fabricRequired,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${quote.mainFabricMeters} ${strings.runningMeters}",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        if (includeSheer) {
                            Text(
                                text = "+ ${quote.sheerFabricMeters} ${strings.runningMeters} (شيفون فوال)",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Section 3: Detailed Quotation Breakdown
            Text(
                text = strings.estimateBreakdown,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    QuoteRow(strings.fabricCost, "${String.format("%.1f", quote.mainFabricPrice * rate)} $currencySymbol")
                    if (includeSheer) {
                        QuoteRow(strings.sheerCost, "${String.format("%.1f", quote.sheerFabricPrice * rate)} $currencySymbol")
                    }
                    QuoteRow(strings.hardwareCost, "${String.format("%.1f", quote.rodCost * rate)} $currencySymbol")
                    if (quote.motorCost > 0) {
                        QuoteRow(strings.smartMotor, "${String.format("%.1f", quote.motorCost * rate)} $currencySymbol")
                    }
                    QuoteRow(strings.tailoringCost, "${String.format("%.1f", quote.tailoringFee * rate)} $currencySymbol")

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = strings.installationCost, style = MaterialTheme.typography.bodySmall)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${String.format("%.1f", quote.installationFee * rate)} $currencySymbol",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Checkbox(
                                checked = includeInstallation,
                                onCheckedChange = { includeInstallation = it }
                            )
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    QuoteRow(strings.subtotal, "${String.format("%.1f", quote.subtotal * rate)} $currencySymbol")
                    QuoteRow(strings.vat, "${String.format("%.1f", quote.vat15 * rate)} $currencySymbol")

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = strings.totalEstimate,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${String.format("%.1f", quote.totalPriceSar * rate)} $currencySymbol",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Order / Quote Action Button
            Button(
                onClick = {
                    Toast.makeText(context, if (appSettings.language == AppLanguage.ARABIC) "تم إرسال طلب عرض السعر بنجاح! سيتواصل معك فريق التفصيل." else "Quotation request submitted successfully!", Toast.LENGTH_LONG).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(strings.requestOrder, fontWeight = FontWeight.Bold)
            }
        }
    }

    if (showTipsDialog) {
        AlertDialog(
            onDismissRequest = { showTipsDialog = false },
            title = {
                Text(strings.measuringGuideTitle, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(strings.measuringGuideText, style = MaterialTheme.typography.bodyMedium)
            },
            confirmButton = {
                TextButton(onClick = { showTipsDialog = false }) {
                    Text(if (appSettings.language == AppLanguage.ARABIC) "فهمت، شكراً" else "Got it, thanks")
                }
            }
        )
    }
}

@Composable
private fun QuoteRow(title: String, amount: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = amount, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
    }
}
