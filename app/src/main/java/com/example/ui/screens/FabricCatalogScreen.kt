package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppStrings
import com.example.data.CurtainDataProvider
import com.example.model.*
import com.example.ui.components.FabricCard
import com.example.ui.components.FabricDetailDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FabricCatalogScreen(
    currentFabric: CurtainFabric,
    currentColor: CurtainColor,
    appSettings: AppSettings,
    onFabricSelectedForAR: (CurtainFabric, CurtainColor) -> Unit
) {
    val strings = AppStrings.get(appSettings.language)

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(FabricCategory.ALL) }
    var minOpacityFilter by remember { mutableFloatStateOf(0f) }
    var showFilterSheet by remember { mutableStateOf(false) }

    var inspectingFabric by remember { mutableStateOf<CurtainFabric?>(null) }

    // Map each fabric to its selected color chip (defaults to first color or current color if matches)
    var selectedColorMap by remember {
        mutableStateOf(
            CurtainDataProvider.fabrics.associate { f ->
                f.id to (if (f.id == currentFabric.id) currentColor else f.colors.first())
            }
        )
    }

    val filteredFabrics = remember(searchQuery, selectedCategory, minOpacityFilter) {
        CurtainDataProvider.fabrics.filter { fabric ->
            val matchesCategory = selectedCategory == FabricCategory.ALL || fabric.category == selectedCategory
            val matchesQuery = searchQuery.isBlank() ||
                    fabric.nameAr.contains(searchQuery, ignoreCase = true) ||
                    fabric.textureDescription.contains(searchQuery, ignoreCase = true) ||
                    fabric.composition.contains(searchQuery, ignoreCase = true)
            val matchesOpacity = fabric.opacityPercent >= minOpacityFilter.toInt()

            matchesCategory && matchesQuery && matchesOpacity
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = strings.fabricsCatalogTitle,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${filteredFabrics.size} ${if (appSettings.language == AppLanguage.ARABIC) "أنواع أقمشة متوفرة" else "fabrics available"}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showFilterSheet = !showFilterSheet }) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = "Filter",
                            tint = if (minOpacityFilter > 0f) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Search Input
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text(strings.searchFabric) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Category Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FabricCategory.values().forEach { cat ->
                    val isSelected = cat == selectedCategory
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat.titleAr, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            // Opacity filter slider dropdown banner
            AnimatedVisibility(visible = showFilterSheet) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${strings.opacity}: ${minOpacityFilter.toInt()}%+",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                            TextButton(onClick = { minOpacityFilter = 0f }) {
                                Text(if (appSettings.language == AppLanguage.ARABIC) "إعادة ضبط" else "Reset", fontSize = 11.sp)
                            }
                        }
                        Slider(
                            value = minOpacityFilter,
                            onValueChange = { minOpacityFilter = it },
                            valueRange = 0f..100f,
                            steps = 9
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Fabric Cards List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(filteredFabrics, key = { it.id }) { fabric ->
                    val activeColor = selectedColorMap[fabric.id] ?: fabric.colors.first()
                    val isCurrentInAR = fabric.id == currentFabric.id

                    FabricCard(
                        fabric = fabric,
                        selectedColor = activeColor,
                        isSelected = isCurrentInAR,
                        currency = appSettings.currency,
                        language = appSettings.language,
                        onSelect = {
                            selectedColorMap = selectedColorMap.toMutableMap().apply {
                                put(fabric.id, activeColor)
                            }
                        },
                        onTryInAR = {
                            onFabricSelectedForAR(fabric, activeColor)
                        },
                        onViewDetails = {
                            inspectingFabric = fabric
                        }
                    )
                }
            }
        }
    }

    // Detail Dialog
    inspectingFabric?.let { fabric ->
        val activeColor = selectedColorMap[fabric.id] ?: fabric.colors.first()
        FabricDetailDialog(
            fabric = fabric,
            currency = appSettings.currency,
            language = appSettings.language,
            onDismiss = { inspectingFabric = null },
            onSelectForAR = {
                onFabricSelectedForAR(fabric, activeColor)
            }
        )
    }
}
