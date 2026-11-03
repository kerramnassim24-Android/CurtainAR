package com.example.model

import androidx.compose.ui.graphics.Color

enum class FabricCategory(val titleAr: String) {
    ALL("الكل"),
    VELVET("مخمل"),
    LINEN("كتان"),
    COTTON("قطن"),
    POLYESTER("بوليستر"),
    BLACKOUT("بلاك آوت عازل"),
    SHEER("شيفون وفوال"),
    JACQUARD("جاكار ودمقس")
}

data class CurtainColor(
    val id: String,
    val nameAr: String,
    val hexColor: Color,
    val shadowColor: Color,
    val highlightColor: Color
)

data class CurtainFabric(
    val id: String,
    val nameAr: String,
    val category: FabricCategory,
    val opacityPercent: Int, // 0 to 100%
    val opacityDescription: String,
    val textureDescription: String,
    val weightGsm: Int,
    val pricePerMeter: Double,
    val composition: String,
    val durability: String,
    val careInstructions: String,
    val lightFiltration: String,
    val recommendedRooms: List<String>,
    val colors: List<CurtainColor>,
    val isSheer: Boolean = false,
    val isBlackout: Boolean = false
)

enum class PleatStyle(
    val id: String,
    val nameAr: String,
    val fullnessRatio: Float,
    val descriptionAr: String,
    val suitableFor: String
) {
    WAVE("wave", "موجة حديثة (Wave / Ripple Fold)", 2.2f, "طيات انسيابية ناعمة على شكل موجة متناسقة، النمط الأكثر طلباً في الديكور المودرن الفندقي.", "الصالات وغرف النوم المودرن"),
    PINCH_PLEAT("pinch", "طيات مزمومة (Pinch Pleat)", 2.5f, "طيات ثلاثية كلاسيكية محبوكة بأناقة في الأعلى تعطي مظهراً فخماً وامتلاءً غنياً.", "المجالس والصالونات الكلاسيكية"),
    EYELET("eyelet", "حلقات معدنية (Eyelet / Grommet)", 1.8f, "حلقات دائرية متينة تخترق القماش وتنزلق مباشرة فوق ماسورة الستارة، سهلة الفك والتركيب.", "غرف المعيشة والمكاتب والشباب"),
    ROMAN_BLIND("roman", "ستارة رومانية (Roman Blind)", 1.1f, "طبقة مسطحة من القماش ترتفع لأعلى بنظام طيات هندسية متتالية عبر حبال أو سحب.", "النوافذ الصغيرة والمطابخ والمكاتب"),
    ROD_POCKET("pocket", "جيب الماسورة (Rod Pocket)", 1.5f, "قناة قماشية مخيطة في الأعلى لإدخال الماسورة مباشرة، بدون حلقات ظاهرة.", "النوافذ الريفية وغرف الأطفال"),
    DOUBLE_LAYER("double", "طبقتين (مخمل + شيفون ناعم)", 2.4f, "مزيج فاخر من طبقة خلفية شيفون للضوء النهاري وطبقة أمامية معتمة للخصوصية المسائية.", "غرف النوم الرئيسية وصالات الاستقبال")
}

enum class RodMaterial(val id: String, val nameAr: String, val color: Color, val metallicAccent: Color) {
    BRUSHED_BRASS("brass", "نحاس مطفي فاخر", Color(0xFFC7A75C), Color(0xFFEADAA2)),
    MATTE_BLACK("black", "أسود عصري مطفي", Color(0xFF222222), Color(0xFF444444)),
    POLISHED_CHROME("chrome", "كروم فضي مصقول", Color(0xFFD4D8DD), Color(0xFFF1F3F5)),
    WALNUT_WOOD("walnut", "خشب جوز طبيعي", Color(0xFF5D4037), Color(0xFF8D6E63)),
    HIDDEN_TRACK("track", "مجرى سقف مخفي (فندقي)", Color(0xFFFFFFFF), Color(0xFFE0E0E0))
}

enum class FinialStyle(val id: String, val nameAr: String) {
    MINIMAL_CAP("cap", "غطاء مسطح عصري"),
    SPHERE("sphere", "كرة كلاسيكية"),
    CRYSTAL("crystal", "بلورة كريستال فاخرة"),
    CYLINDER("cylinder", "أسطوانة نحاسية")
}

enum class TiebackStyle(val id: String, val nameAr: String) {
    NONE("none", "بدون أربطة (منسدلة حرة)"),
    VELVET_TASSEL("tassel", "شرابة مخملية ملكية"),
    MAGNETIC_GOLD("magnetic", "مشابك مغناطيسية ذهبية"),
    FABRIC_STRAP("strap", "حزام من نفس قماش الستارة")
}

enum class LightingEnvironment(val id: String, val nameAr: String, val ambientFactor: Float, val sunColor: Color) {
    DAYLIGHT("day", "ضوء النهار الساطع", 1.0f, Color(0xFFFFFDF5)),
    GOLDEN_HOUR("golden", "الغروب الدافئ", 0.85f, Color(0xFFFFECC4)),
    NIGHT_WARM("night", "إضاءة ليلية دافئة", 0.7f, Color(0xFFFFD59E))
}

data class WindowMeasurements(
    val widthCm: Float = 220f,
    val heightCm: Float = 260f,
    val extensionCm: Float = 25f, // extension beyond window edge on both sides
    val floorClearanceCm: Float = 0f, // 0 = touching floor, +2 = hovering, -5 = puddling
    val fullnessFactor: Float = 2.0f
) {
    val totalRodWidthCm: Float get() = widthCm + (extensionCm * 2)
    val totalHeightCm: Float get() = heightCm + 15f - floorClearanceCm // 15cm above window frame
    
    // Calculate required running meters of fabric (assuming standard 280cm fabric width roll)
    fun calculateFabricMeters(fullness: Float): Float {
        val totalTrackWidthMeters = totalRodWidthCm / 100f
        val gatheredWidth = totalTrackWidthMeters * fullness
        // Standard curtain fabrics come in 2.8m - 3.0m drop roll, so width of window = running meters needed
        return String.format("%.2f", gatheredWidth).toFloat()
    }
}

data class CurtainHardware(
    val rodMaterial: RodMaterial = RodMaterial.BRUSHED_BRASS,
    val finial: FinialStyle = FinialStyle.MINIMAL_CAP,
    val tieback: TiebackStyle = TiebackStyle.MAGNETIC_GOLD,
    val isSmartMotorized: Boolean = false
)

data class RoomPreset(
    val id: String,
    val nameAr: String,
    val type: String,
    val drawableRes: Int?,
    val defaultWindowWidth: Float,
    val defaultWindowHeight: Float
)

data class SavedCurtainDesign(
    val id: String,
    val title: String,
    val roomType: String,
    val fabricId: String,
    val fabricName: String,
    val colorId: String,
    val colorName: String,
    val pleatStyle: PleatStyle,
    val hardware: CurtainHardware,
    val measurements: WindowMeasurements,
    val fabricMetersNeeded: Float,
    val totalPriceSar: Double,
    val snapshotPath: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
