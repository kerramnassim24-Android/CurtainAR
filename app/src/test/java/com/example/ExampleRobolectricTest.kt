package com.example

import com.example.model.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class CurtainUnitTest {

    @Test
    fun testWindowCalculations() {
        val window = WindowMeasurements(
            widthCm = 240f,
            heightCm = 260f,
            extensionCm = 30f,
            fullnessFactor = 2.2f
        )
        // total rod = 240 + 60 = 300cm = 3.0m
        assertEquals(300f, window.totalRodWidthCm, 0.01f)
        // 3.0 * 2.2 = 6.6 meters
        val fabricMeters = window.calculateFabricMeters(2.2f)
        assertEquals(6.6f, fabricMeters, 0.01f)
    }

    @Test
    fun testSettingsDefaultValues() {
        val settings = AppSettings()
        assertEquals(AppLanguage.ARABIC, settings.language)
        assertEquals(AppCurrency.DZD, settings.currency)
        assertEquals(MeasurementUnit.CM, settings.unit)
    }
}
