package com.example.data

import androidx.compose.ui.graphics.Color
import com.example.R
import com.example.model.*

object CurtainDataProvider {

    // Palettes
    val velvetColors = listOf(
        CurtainColor("v_emerald", "أخضر زمردي ملكي", Color(0xFF1E3F30), Color(0xFF0F221A), Color(0xFF33604B)),
        CurtainColor("v_navy", "كحلي ليلي فاخر", Color(0xFF1C2C42), Color(0xFF0F1926), Color(0xFF2C4364)),
        CurtainColor("v_burgundy", "عنابي أرستقراطي", Color(0xFF5B1B26), Color(0xFF3B1017), Color(0xFF7E2B3A)),
        CurtainColor("v_charcoal", "رمادي فحمي هادئ", Color(0xFF2B2A2A), Color(0xFF191818), Color(0xFF454343)),
        CurtainColor("v_champagne", "بيج شامبانيا دافئ", Color(0xFFD6C4A5), Color(0xFFAC9B7E), Color(0xFFEDE0C8)),
        CurtainColor("v_gold", "ذهبي برونزي معتق", Color(0xFFB58E4A), Color(0xFF7E602B), Color(0xFFDAB774))
    )

    val linenColors = listOf(
        CurtainColor("l_natural", "كتان طبيعي خام", Color(0xFFE4DACB), Color(0xFFB8AE9F), Color(0xFFFAF5ED)),
        CurtainColor("l_sand", "بيج رملي دافئ", Color(0xFFD9CCA9), Color(0xFFA79B7B), Color(0xFFF3E9CD)),
        CurtainColor("l_taupe", "موكا ترابي بوهيمي", Color(0xFF988574), Color(0xFF6B5C4E), Color(0xFFBCAAA8)),
        CurtainColor("l_olive", "زيتي هادئ ناعم", Color(0xFF6B705C), Color(0xFF484B3E), Color(0xFF8A9075)),
        CurtainColor("l_terracotta", "طمي تيراكوتا دافئ", Color(0xFFA65B43), Color(0xFF733B29), Color(0xFFC7775D))
    )

    val cottonColors = listOf(
        CurtainColor("c_white", "أبيض ناصع منعش", Color(0xFFFBFBFB), Color(0xFFD2D2D2), Color(0xFFFFFFFF)),
        CurtainColor("c_ivory", "عاجي كلاسيكي هادئ", Color(0xFFF4EDE2), Color(0xFFC9C1B6), Color(0xFFFFFDF9)),
        CurtainColor("c_lightgrey", "رمادي لؤلؤي بارد", Color(0xFFD2D5D8), Color(0xFFA6ABB0), Color(0xFFEBECEE)),
        CurtainColor("c_softblue", "سماوي باستيل مريح", Color(0xFF9FB6C9), Color(0xFF748A9C), Color(0xFFC2D5E4)),
        CurtainColor("c_sage", "ميرمية باستيل ناعم", Color(0xFFA3B19B), Color(0xFF788571), Color(0xFFC5D1BD))
    )

    val polyesterColors = listOf(
        CurtainColor("p_hotelgrey", "رمادي فندقي فاخر", Color(0xFF7E8184), Color(0xFF56585B), Color(0xFFA7AAB0)),
        CurtainColor("p_navy", "أزرق كحلي عصري", Color(0xFF283B55), Color(0xFF192535), Color(0xFF425A7D)),
        CurtainColor("p_warmbeige", "بيج شتوي دافئ", Color(0xFFC7BBA8), Color(0xFF968B7A), Color(0xFFE5DBD0)),
        CurtainColor("p_coffee", "بني قهوة إسبريسو", Color(0xFF493A32), Color(0xFF2E241F), Color(0xFF6C574B))
    )

    val blackoutColors = listOf(
        CurtainColor("b_deepblack", "أسود داكن تام", Color(0xFF151515), Color(0xFF050505), Color(0xFF2B2B2B)),
        CurtainColor("b_darknavy", "كحلي عاتم ليلي", Color(0xFF111C2B), Color(0xFF070C13), Color(0xFF22344D)),
        CurtainColor("b_charcoal", "رصاصي فحمي عازل", Color(0xFF26292D), Color(0xFF151719), Color(0xFF43474D)),
        CurtainColor("b_warmgrey", "رمادي حجري معتم", Color(0xFF575757), Color(0xFF383838), Color(0xFF757575)),
        CurtainColor("b_darkbeige", "بيج غامق معتم", Color(0xFF8E8373), Color(0xFF5C5448), Color(0xFFB1A594))
    )

    val sheerColors = listOf(
        CurtainColor("s_purewhite", "أبيض ملائكي ناصع", Color(0xFFFAFAFA), Color(0xFFE2E2E2), Color(0xFFFFFFFF)),
        CurtainColor("s_sunivory", "عاجي مشمس دافئ", Color(0xFFFFF7EA), Color(0xFFE4D7C2), Color(0xFFFFFDF5)),
        CurtainColor("s_softchampagne", "شامبانيا شفافة رقيقة", Color(0xFFF3E8D7), Color(0xFFCFBFAB), Color(0xFFFAF3E9)),
        CurtainColor("s_silversheer", "فضي ضبابي خفيف", Color(0xFFE3E5E8), Color(0xFFBFC2C6), Color(0xFFF1F3F5))
    )

    val jacquardColors = listOf(
        CurtainColor("j_goldcream", "ذهبي ملكي مع كريمي", Color(0xFFCDB47C), Color(0xFF99814F), Color(0xFFEADBAB)),
        CurtainColor("j_damaskblue", "أزرق دمشقي مع فضي", Color(0xFF384D67), Color(0xFF233244), Color(0xFF577194)),
        CurtainColor("j_antiquebronze", "برونز معتق فاخر", Color(0xFF946F4C), Color(0xFF674C32), Color(0xFFBC946D))
    )

    val satinColors = listOf(
        CurtainColor("st_silversilk", "فضي حريري متلألئ", Color(0xFFD6DBDE), Color(0xFF9DA3A8), Color(0xFFF5F7F8)),
        CurtainColor("st_dustyrose", "وردي غباري ساتان", Color(0xFFC78B96), Color(0xFF965B66), Color(0xFFE5B0BA)),
        CurtainColor("st_copper", "نحاسي براق أنيق", Color(0xFFB26B48), Color(0xFF7E4529), Color(0xFFD48A66))
    )

    // Complete Curated Fabrics List
    val fabrics: List<CurtainFabric> = listOf(
        CurtainFabric(
            id = "fabric_velvet",
            nameAr = "المخمل الملكي الفاخر (Royal Velvet)",
            category = FabricCategory.VELVET,
            opacityPercent = 90,
            opacityDescription = "حجب عالي للضوء بنسبة 90% وعزل صوتي رائع",
            textureDescription = "ملمس ناعم وثقيل مع لمعان أرستقراطي خافت وانسيابية فخمة",
            weightGsm = 430,
            pricePerMeter = 85.0,
            composition = "بوليستر مخملي كثيف 100% (نسيج إيطالي مضاد للوبر)",
            durability = "فائقة جداً، ألوان ثابتة مقاومة للبهتان والتلاشي",
            careInstructions = "غسيل جاف أو تنظيف بخاري مخصص للمخمل الفاخر",
            lightFiltration = "يحجب الأضواء القوية والوهج ويوفر دفئاً وعزلاً صوتياً ممتزاً",
            recommendedRooms = listOf("صالون الضيوف", "غرفة النوم الرئيسية", "المجالس الفخمة"),
            colors = velvetColors
        ),
        CurtainFabric(
            id = "fabric_linen",
            nameAr = "الكتان الطبيعي البوهيمي (Natural Slub Linen)",
            category = FabricCategory.LINEN,
            opacityPercent = 55,
            opacityDescription = "تعتيم نصفي بنسبة 55% يفلتر الضوء الطبيعي بجمال",
            textureDescription = "خيوط طبيعية متعرجة بلمسة ترابية منعشة ومظهر عضوي أنيق",
            weightGsm = 320,
            pricePerMeter = 65.0,
            composition = "كتان بلجيكي طبيعي 60%، قطن صافي 40%",
            durability = "قماش متين يدوم لسنوات ويزداد نعومة مع الغسيل",
            careInstructions = "غسيل لطيف بدرجة حرارة 30 مئوية وتجفيف بالهواء الطبيعي",
            lightFiltration = "يسمح بمرور ضوء النهار الهادئ مع الحفاظ على الخصوصية التامة",
            recommendedRooms = listOf("غرفة المعيشة المودرن", "المطبخ المفتوح", "غرف النوم الهادئة"),
            colors = linenColors
        ),
        CurtainFabric(
            id = "fabric_blackout",
            nameAr = "بلاك آوت عازل حراري وضوضاء (Thermal Blackout)",
            category = FabricCategory.BLACKOUT,
            opacityPercent = 100,
            opacityDescription = "حجب ضوء كامل بنسبة 100% وظلام فندقي تام",
            textureDescription = "نسيج ثلاثي الطبقات مع بطانة عازلة للحرارة وأشعة الشمس الحارقة",
            weightGsm = 460,
            pricePerMeter = 95.0,
            composition = "نسيج ناعم ثلاثي النسيج مع عازل حراري أكريليك آمن",
            durability = "عالية جداً، يحمي الأثاث والأرضيات من أشعة الشمس المباشرة",
            careInstructions = "تنظيف موضعي بالمسح أو غسيل جاف خفيف",
            lightFiltration = "عتمة كاملة تضمن نوماً عميقاً ومريحاً حتى في ذروة الظهيرة",
            recommendedRooms = listOf("غرف النوم", "غرف السينما المنزلية", "المكاتب وغرف الأطفال"),
            colors = blackoutColors,
            isBlackout = true
        ),
        CurtainFabric(
            id = "fabric_cotton",
            nameAr = "القطن العضوي النقي (Organic Pure Cotton)",
            category = FabricCategory.COTTON,
            opacityPercent = 65,
            opacityDescription = "تعتيم متوازن بنسبة 65% مع تهوية طبيعية ممتازة",
            textureDescription = "ناعم ومسامي، مظهر غير لامع (Matt)، طبيعي ولطيف على الحواس",
            weightGsm = 280,
            pricePerMeter = 50.0,
            composition = "قطن ممشط عضوي 100% خالٍ من الكيماويات الضارة",
            durability = "ممتازة للاستخدام اليومي المستمر وتتحمل الغسيل المتكرر",
            careInstructions = "غسيل بالغسالة بدرجة عادية وكي بحرارة متوسطة",
            lightFiltration = "ينشر الضوء بلطف ويقلل وهج الشمس الحاد داخل الغرفة",
            recommendedRooms = listOf("غرف الأطفال", "غرفة المعيشة العائلية", "المكاتب الدراسية"),
            colors = cottonColors
        ),
        CurtainFabric(
            id = "fabric_polyester",
            nameAr = "البوليستر الفندقي عالي التحمل (Heavy Polyester)",
            category = FabricCategory.POLYESTER,
            opacityPercent = 75,
            opacityDescription = "حجب مريح بنسبة 75% وخصوصية كاملة",
            textureDescription = "نسيج متماسك ومقاوم للبقع والتجعد، مظهر فندقي راقٍ وانسيابي",
            weightGsm = 310,
            pricePerMeter = 42.0,
            composition = "ألياف بوليستر دقيقة ذات كثافة عالية وملمس ناعم",
            durability = "مقاومة استثنائية للتجاعيد، لا يحتاج لكي مستمر",
            careInstructions = "غسيل آلي سريع، سريع الجفاف ومضاد للتقلص",
            lightFiltration = "توازن مثالي بين الخصوصية وعزل جزء كبير من الضوء الخارجي",
            recommendedRooms = listOf("الشقق الفندقية", "المكاتب والشركات", "الصالات اليومية"),
            colors = polyesterColors
        ),
        CurtainFabric(
            id = "fabric_sheer",
            nameAr = "الشيفون والفوال الحريري (Soft Silk Sheer)",
            category = FabricCategory.SHEER,
            opacityPercent = 15,
            opacityDescription = "شفافية فائقة بنسبة 15% تضفي إحساساً هوائياً حراً",
            textureDescription = "انسيابي وخفيف كالريش، يتهدل برقة بالغة وينعكس مع نسيم الهواء",
            weightGsm = 95,
            pricePerMeter = 35.0,
            composition = "فوال شيفون ناعم مع ألياف حريرية دقيقة",
            durability = "ناعمة ورقيقة، تقاوم اصفرار أشعة الشمس",
            careInstructions = "غسيل لطيف جداً داخل كيس غسيل شبكي أو غسيل يدوي",
            lightFiltration = "يحول أشعة الشمس المباشرة إلى ضوء رومانسي ناعم ومريح",
            recommendedRooms = listOf("طبقة ثانية تحت الستائر الرئيسية", "الصالات الكبيرة", "الشرفات"),
            colors = sheerColors,
            isSheer = true
        ),
        CurtainFabric(
            id = "fabric_jacquard",
            nameAr = "الجاكار والدمقس الملكي (Royal Damask Jacquard)",
            category = FabricCategory.JACQUARD,
            opacityPercent = 80,
            opacityDescription = "تعتيم أنيق بنسبة 80% مع طابع قصور وأرستقراطية",
            textureDescription = "نقوش كلاسيكية بارزة ومنسوجة بعناية فائقة تعكس الفخامة المطلقة",
            weightGsm = 390,
            pricePerMeter = 110.0,
            composition = "مزيج خيوط حريرية مع بوليستر منسوج بنول الجاكار",
            durability = "صلابة ومتانة أسطورية مع مقاومة للاحتكاك والتلف",
            careInstructions = "غسيل جاف متخصص للحفاظ على لمعان وبريق النقوش المنسوجة",
            lightFiltration = "حجب ممتاز للأضواء والخصوصية العالية مع هيبة بصرية طاغية",
            recommendedRooms = listOf("المجالس الكبرى", "صالونات الاستقبال الفخمة", "الفلل والقصور"),
            colors = jacquardColors
        ),
        CurtainFabric(
            id = "fabric_satin",
            nameAr = "الساتان الحريري اللامع (Lustrous Satin Silk)",
            category = FabricCategory.COTTON,
            opacityPercent = 70,
            opacityDescription = "حجب بنسبة 70% مع بريق مسرحي ساحر",
            textureDescription = "سطح حريري أملس وشديد النعومة يعكس الإضاءات الليلية بإبهار",
            weightGsm = 260,
            pricePerMeter = 75.0,
            composition = "ألياف ساتان حريرية فائقة اللمعان والانسياب",
            durability = "أناقة دائمة ولون مشبع براق لا يتغير",
            careInstructions = "غسيل يدوي بارد أو غسيل جاف، وكي بالبخار فقط من الخلف",
            lightFiltration = "يعكس الضوء الساقط عليه ويعطي إيحاءً باتساع الغرفة وفخامتها",
            recommendedRooms = listOf("غرف النوم الرومانسية", "غرف الملابس الراقية", "غرف الجلوس الخاصة"),
            colors = satinColors
        )
    )

    // Pre-designed Room Templates for AR testing when camera isn't desired
    val roomPresets = listOf(
        RoomPreset(
            id = "room_living",
            nameAr = "صالة معيشة عصرية واسعة",
            type = "غرفة معيشة",
            drawableRes = R.drawable.room_modern_living,
            defaultWindowWidth = 240f,
            defaultWindowHeight = 270f
        ),
        RoomPreset(
            id = "room_bedroom",
            nameAr = "غرفة نوم ماستر فاخرة",
            type = "غرفة نوم",
            drawableRes = R.drawable.room_luxury_bedroom,
            defaultWindowWidth = 200f,
            defaultWindowHeight = 250f
        )
    )

    // Calculate quotation breakdown
    fun calculateQuote(
        fabric: CurtainFabric,
        measurements: WindowMeasurements,
        pleatStyle: PleatStyle,
        hardware: CurtainHardware,
        includeSheerLayer: Boolean = false,
        sheerFabric: CurtainFabric? = null,
        includeInstallation: Boolean = true
    ): QuoteBreakdown {
        val mainMeters = measurements.calculateFabricMeters(pleatStyle.fullnessRatio)
        val mainFabricCost = mainMeters * fabric.pricePerMeter

        val sheerMeters = if (includeSheerLayer) {
            measurements.calculateFabricMeters(2.0f)
        } else 0f
        val sheerPricePerMeter = sheerFabric?.pricePerMeter ?: 35.0
        val sheerFabricCost = sheerMeters * sheerPricePerMeter

        // Hardware cost: Rod per meter + Finials + Brackets + Motor
        val rodLengthMeters = measurements.totalRodWidthCm / 100f
        val rodBasePricePerMeter = when (hardware.rodMaterial) {
            RodMaterial.BRUSHED_BRASS -> 75.0
            RodMaterial.MATTE_BLACK -> 55.0
            RodMaterial.POLISHED_CHROME -> 65.0
            RodMaterial.WALNUT_WOOD -> 90.0
            RodMaterial.HIDDEN_TRACK -> 45.0
        }
        val rodCost = rodLengthMeters * rodBasePricePerMeter * (if (includeSheerLayer) 1.8 else 1.0)
        val motorCost = if (hardware.isSmartMotorized) 450.0 else 0.0
        val accessoriesCost = when (hardware.tieback) {
            TiebackStyle.NONE -> 0.0
            TiebackStyle.VELVET_TASSEL -> 60.0
            TiebackStyle.MAGNETIC_GOLD -> 45.0
            TiebackStyle.FABRIC_STRAP -> 25.0
        }

        // Tailoring & Craftsmanship fee: 25 SAR per meter of fabric
        val tailoringFee = (mainMeters + sheerMeters) * 25.0
        val installationFee = if (includeInstallation) 120.0 else 0.0

        val subtotal = mainFabricCost + sheerFabricCost + rodCost + motorCost + accessoriesCost + tailoringFee + installationFee
        val vat15 = subtotal * 0.15
        val total = subtotal + vat15

        return QuoteBreakdown(
            mainFabricMeters = mainMeters,
            mainFabricPrice = mainFabricCost,
            sheerFabricMeters = sheerMeters,
            sheerFabricPrice = sheerFabricCost,
            rodCost = rodCost,
            motorCost = motorCost,
            accessoriesCost = accessoriesCost,
            tailoringFee = tailoringFee,
            installationFee = installationFee,
            subtotal = subtotal,
            vat15 = vat15,
            totalPriceSar = total
        )
    }

    data class QuoteBreakdown(
        val mainFabricMeters: Float,
        val mainFabricPrice: Double,
        val sheerFabricMeters: Float,
        val sheerFabricPrice: Double,
        val rodCost: Double,
        val motorCost: Double,
        val accessoriesCost: Double,
        val tailoringFee: Double,
        val installationFee: Double,
        val subtotal: Double,
        val vat15: Double,
        val totalPriceSar: Double
    )
}
