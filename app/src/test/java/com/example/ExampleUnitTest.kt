package com.example

import com.example.data.CurtainDataProvider
import com.example.model.*
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testFabricCatalogContainsRequiredTypes() {
        val fabrics = CurtainDataProvider.fabrics
        assertTrue("Fabrics list should not be empty", fabrics.isNotEmpty())

        val hasVelvet = fabrics.any { it.category == FabricCategory.VELVET }
        val hasLinen = fabrics.any { it.category == FabricCategory.LINEN }
        val hasCotton = fabrics.any { it.category == FabricCategory.COTTON }
        val hasPolyester = fabrics.any { it.category == FabricCategory.POLYESTER }
        val hasBlackout = fabrics.any { it.isBlackout }
        val hasSheer = fabrics.any { it.isSheer }

        assertTrue("Must include velvet fabric", hasVelvet)
        assertTrue("Must include linen fabric", hasLinen)
        assertTrue("Must include cotton fabric", hasCotton)
        assertTrue("Must include polyester fabric", hasPolyester)
        assertTrue("Must include blackout fabric", hasBlackout)
        assertTrue("Must include sheer/chiffon fabric", hasSheer)
    }

    @Test
    fun testFabricOpacityAndProperties() {
        val blackout = CurtainDataProvider.fabrics.first { it.isBlackout }
        assertEquals("Blackout fabric opacity must be 100%", 100, blackout.opacityPercent)

        val sheer = CurtainDataProvider.fabrics.first { it.isSheer }
        assertTrue("Sheer fabric opacity must be under 30%", sheer.opacityPercent < 30)

        val velvet = CurtainDataProvider.fabrics.first { it.category == FabricCategory.VELVET }
        assertTrue("Velvet fabric must have rich weight (> 350 GSM)", velvet.weightGsm > 350)
    }

    @Test
    fun testWindowFabricMetersCalculation() {
        // Window 200cm width + (25cm extension * 2) = 250cm total rod width (2.5 meters)
        // With 2.0x fullness: 2.5 * 2.0 = 5.0 meters of fabric needed
        val window = WindowMeasurements(widthCm = 200f, heightCm = 250f, extensionCm = 25f)
        val meters = window.calculateFabricMeters(2.0f)
        assertEquals(5.0f, meters, 0.05f)
    }

    @Test
    fun testQuoteCalculation() {
        val velvet = CurtainDataProvider.fabrics.first { it.category == FabricCategory.VELVET }
        val window = WindowMeasurements(widthCm = 200f, heightCm = 250f, extensionCm = 25f)
        val quote = CurtainDataProvider.calculateQuote(
            fabric = velvet,
            measurements = window,
            pleatStyle = PleatStyle.WAVE,
            hardware = CurtainHardware(),
            includeSheerLayer = false,
            includeInstallation = false
        )

        assertTrue("Fabric meters should be positive", quote.mainFabricMeters > 0f)
        assertTrue("Main fabric price should be calculated", quote.mainFabricPrice > 0.0)
        assertTrue("Total price should be positive", quote.totalPriceSar > 0.0)
    }
}
