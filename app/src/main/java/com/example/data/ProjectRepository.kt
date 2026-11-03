package com.example.data

import android.content.Context
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class ProjectRepository(private val context: Context) {

    private val _savedDesigns = MutableStateFlow<List<SavedCurtainDesign>>(emptyList())
    val savedDesigns: StateFlow<List<SavedCurtainDesign>> = _savedDesigns.asStateFlow()

    init {
        // Populate with high quality initial inspirations
        loadInitialSampleProjects()
    }

    private fun loadInitialSampleProjects() {
        val velvet = CurtainDataProvider.fabrics.first { it.id == "fabric_velvet" }
        val linen = CurtainDataProvider.fabrics.first { it.id == "fabric_linen" }
        val blackout = CurtainDataProvider.fabrics.first { it.id == "fabric_blackout" }

        val samples = listOf(
            SavedCurtainDesign(
                id = "sample_1",
                title = "صالون الضيوف الملكي",
                roomType = "صالون استقبال",
                fabricId = velvet.id,
                fabricName = velvet.nameAr,
                colorId = velvet.colors[0].id, // Emerald Green
                colorName = velvet.colors[0].nameAr,
                pleatStyle = PleatStyle.DOUBLE_LAYER,
                hardware = CurtainHardware(
                    rodMaterial = RodMaterial.BRUSHED_BRASS,
                    finial = FinialStyle.MINIMAL_CAP,
                    tieback = TiebackStyle.MAGNETIC_GOLD,
                    isSmartMotorized = true
                ),
                measurements = WindowMeasurements(widthCm = 260f, heightCm = 280f, extensionCm = 30f),
                fabricMetersNeeded = 7.04f,
                totalPriceSar = 1845.0,
                createdAt = System.currentTimeMillis() - 86400000L
            ),
            SavedCurtainDesign(
                id = "sample_2",
                title = "غرفة النوم المودرن الهادئة",
                roomType = "غرفة نوم ماستر",
                fabricId = linen.id,
                fabricName = linen.nameAr,
                colorId = linen.colors[0].id, // Natural Linen
                colorName = linen.colors[0].nameAr,
                pleatStyle = PleatStyle.WAVE,
                hardware = CurtainHardware(
                    rodMaterial = RodMaterial.MATTE_BLACK,
                    finial = FinialStyle.MINIMAL_CAP,
                    tieback = TiebackStyle.NONE,
                    isSmartMotorized = false
                ),
                measurements = WindowMeasurements(widthCm = 200f, heightCm = 250f, extensionCm = 25f),
                fabricMetersNeeded = 5.5f,
                totalPriceSar = 920.0,
                createdAt = System.currentTimeMillis() - 172800000L
            ),
            SavedCurtainDesign(
                id = "sample_3",
                title = "ستارة السينما المنزلية العازلة",
                roomType = "غرفة المعيشة والسينما",
                fabricId = blackout.id,
                fabricName = blackout.nameAr,
                colorId = blackout.colors[0].id, // Deep Black
                colorName = blackout.colors[0].nameAr,
                pleatStyle = PleatStyle.PINCH_PLEAT,
                hardware = CurtainHardware(
                    rodMaterial = RodMaterial.HIDDEN_TRACK,
                    finial = FinialStyle.MINIMAL_CAP,
                    tieback = TiebackStyle.NONE,
                    isSmartMotorized = true
                ),
                measurements = WindowMeasurements(widthCm = 300f, heightCm = 290f, extensionCm = 30f),
                fabricMetersNeeded = 9.0f,
                totalPriceSar = 2150.0,
                createdAt = System.currentTimeMillis() - 259200000L
            )
        )
        _savedDesigns.value = samples
    }

    fun saveDesign(design: SavedCurtainDesign) {
        val current = _savedDesigns.value.toMutableList()
        val index = current.indexOfFirst { it.id == design.id }
        if (index != -1) {
            current[index] = design
        } else {
            current.add(0, design)
        }
        _savedDesigns.value = current
    }

    fun deleteDesign(id: String) {
        _savedDesigns.value = _savedDesigns.value.filter { it.id != id }
    }
}
