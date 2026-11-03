package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.AppStrings
import com.example.data.CurtainDataProvider
import com.example.data.ProjectRepository
import com.example.model.*
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme

enum class ScreenTab(val index: Int) {
    AR(0),
    FABRICS(1),
    STUDIO(2),
    CALCULATOR(3),
    PROJECTS(4),
    SETTINGS(5)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurtainApp()
        }
    }
}

@Composable
fun CurtainApp() {
    val context = LocalContext.current
    val repository = remember { ProjectRepository(context) }
    val savedDesigns by repository.savedDesigns.collectAsStateWithLifecycle()

    var appSettings by remember {
        mutableStateOf(AppSettings(language = AppLanguage.ARABIC, currency = AppCurrency.DZD, unit = MeasurementUnit.CM))
    }

    val isDark = when (appSettings.isDarkMode) {
        true -> true
        false -> false
        null -> isSystemInDarkTheme()
    }

    val strings = AppStrings.get(appSettings.language)

    // Current Active Curtain Configuration
    var currentFabric by remember { mutableStateOf(CurtainDataProvider.fabrics.first()) }
    var currentColor by remember { mutableStateOf(currentFabric.colors.first()) }
    var currentStyle by remember { mutableStateOf(PleatStyle.WAVE) }
    var currentHardware by remember { mutableStateOf(CurtainHardware()) }
    var includeSheer by remember { mutableStateOf(true) }
    val sheerColor = remember { CurtainDataProvider.sheerColors[1] } // Sun Ivory
    var measurements by remember { mutableStateOf(WindowMeasurements()) }

    var currentTab by remember { mutableStateOf(ScreenTab.AR) }

    // RTL for Arabic, LTR for English
    val layoutDirection = if (appSettings.language == AppLanguage.ARABIC) LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        MyApplicationTheme(darkTheme = isDark) {
            // Handle system back navigation
            BackHandler(enabled = currentTab != ScreenTab.AR) {
                currentTab = ScreenTab.AR
            }

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surface,
                        tonalElevation = 6.dp
                    ) {
                        val navItems = listOf(
                            Triple(ScreenTab.AR, strings.tabAR, Icons.Default.ViewInAr),
                            Triple(ScreenTab.FABRICS, strings.tabFabrics, Icons.Default.Layers),
                            Triple(ScreenTab.STUDIO, strings.tabStudio, Icons.Default.Palette),
                            Triple(ScreenTab.CALCULATOR, strings.tabCalculator, Icons.Default.Calculate),
                            Triple(ScreenTab.PROJECTS, strings.tabProjects, Icons.Default.Bookmarks),
                            Triple(ScreenTab.SETTINGS, strings.tabSettings, Icons.Default.Settings)
                        )

                        navItems.forEach { (tab, label, icon) ->
                            val isSelected = currentTab == tab
                            NavigationBarItem(
                                selected = isSelected,
                                onClick = { currentTab = tab },
                                icon = {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = label,
                                        tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                },
                                label = {
                                    Text(
                                        text = label,
                                        fontSize = 10.sp,
                                        maxLines = 1,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                                )
                            )
                        }
                    }
                }
            ) { innerPadding ->
                AnimatedContent(
                    targetState = currentTab,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = innerPadding.calculateBottomPadding()),
                    label = "TabContent"
                ) { tab ->
                    when (tab) {
                        ScreenTab.AR -> {
                            ARStudioScreen(
                                currentFabric = currentFabric,
                                currentColor = currentColor,
                                currentStyle = currentStyle,
                                currentHardware = currentHardware,
                                includeSheer = includeSheer,
                                sheerColor = sheerColor,
                                appSettings = appSettings,
                                measurements = measurements,
                                onFabricChange = { f, c ->
                                    currentFabric = f
                                    currentColor = c
                                },
                                onColorChange = { currentColor = it },
                                onStyleChange = { currentStyle = it },
                                onHardwareChange = { currentHardware = it },
                                onSheerToggle = { includeSheer = it },
                                onSaveProject = { repository.saveDesign(it) },
                                onNavigateToCalculator = { currentTab = ScreenTab.CALCULATOR }
                            )
                        }
                        ScreenTab.FABRICS -> {
                            FabricCatalogScreen(
                                currentFabric = currentFabric,
                                currentColor = currentColor,
                                appSettings = appSettings,
                                onFabricSelectedForAR = { fabric, color ->
                                    currentFabric = fabric
                                    currentColor = color
                                    currentTab = ScreenTab.AR
                                }
                            )
                        }
                        ScreenTab.STUDIO -> {
                            CurtainCustomizerScreen(
                                currentFabric = currentFabric,
                                currentColor = currentColor,
                                currentStyle = currentStyle,
                                currentHardware = currentHardware,
                                includeSheer = includeSheer,
                                measurements = measurements,
                                appSettings = appSettings,
                                onFabricChange = { f, c ->
                                    currentFabric = f
                                    currentColor = c
                                },
                                onColorChange = { currentColor = it },
                                onStyleChange = { currentStyle = it },
                                onHardwareChange = { currentHardware = it },
                                onSheerToggle = { includeSheer = it },
                                onSaveDesign = { repository.saveDesign(it) },
                                onLaunchAR = { currentTab = ScreenTab.AR }
                            )
                        }
                        ScreenTab.CALCULATOR -> {
                            MeasurementCalculatorScreen(
                                currentFabric = currentFabric,
                                currentColor = currentColor,
                                currentStyle = currentStyle,
                                currentHardware = currentHardware,
                                includeSheer = includeSheer,
                                measurements = measurements,
                                appSettings = appSettings,
                                onMeasurementsChange = { measurements = it },
                                onNavigateToAR = { currentTab = ScreenTab.AR }
                            )
                        }
                        ScreenTab.PROJECTS -> {
                            SavedProjectsScreen(
                                savedDesigns = savedDesigns,
                                appSettings = appSettings,
                                onOpenDesignInAR = { design ->
                                    CurtainDataProvider.fabrics.find { it.id == design.fabricId }?.let { fab ->
                                        currentFabric = fab
                                        fab.colors.find { it.id == design.colorId }?.let { col ->
                                            currentColor = col
                                        }
                                    }
                                    currentStyle = design.pleatStyle
                                    currentHardware = design.hardware
                                    measurements = design.measurements
                                    currentTab = ScreenTab.AR
                                },
                                onDeleteDesign = { repository.deleteDesign(it) },
                                onCreateNew = { currentTab = ScreenTab.STUDIO }
                            )
                        }
                        ScreenTab.SETTINGS -> {
                            SettingsScreen(
                                appSettings = appSettings,
                                onSettingsChange = { appSettings = it }
                            )
                        }
                    }
                }
            }
        }
    }
}
