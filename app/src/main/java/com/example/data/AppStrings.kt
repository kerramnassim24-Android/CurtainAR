package com.example.data

import com.example.model.AppLanguage

object AppStrings {
    fun get(language: AppLanguage): Strings = if (language == AppLanguage.ARABIC) ArabicStrings else EnglishStrings

    interface Strings {
        val appName: String
        val tabAR: String
        val tabStudio: String
        val tabFabrics: String
        val tabCalculator: String
        val tabProjects: String
        val tabSettings: String

        // AR Screen
        val arTitle: String
        val liveCamera: String
        val sampleRoom: String
        val switchRoom: String
        val openCloseCurtains: String
        val curtainOpen: String
        val curtainClosed: String
        val adjustCorners: String
        val resetCorners: String
        val captureSnapshot: String
        val snapshotSaved: String
        val lighting: String
        val daylight: String
        val goldenHour: String
        val nightWarm: String
        val width: String
        val height: String
        val selectColor: String
        val selectFabric: String
        val tryFabric: String
        val cameraPermissionNeeded: String
        val grantPermission: String
        val continueWithSample: String
        val dragToPosition: String

        // Fabrics Screen
        val fabricsCatalogTitle: String
        val fabricSpecs: String
        val opacity: String
        val texture: String
        val weight: String
        val composition: String
        val durability: String
        val care: String
        val lightFilter: String
        val recommendedRooms: String
        val pricePerMeter: String
        val tryInAR: String
        val filterCategory: String
        val searchFabric: String
        val allFabrics: String
        val gsm: String

        // Customizer & Styles
        val customizerTitle: String
        val stepRoom: String
        val stepFabric: String
        val stepPleats: String
        val stepHardware: String
        val pleatStyle: String
        val fullnessRatio: String
        val hardwareRod: String
        val finials: String
        val tiebacks: String
        val smartMotor: String
        val smartMotorDesc: String
        val includeSheer: String
        val includeSheerDesc: String
        val nextStep: String
        val prevStep: String
        val saveDesign: String
        val previewInAR: String

        // Calculator Screen
        val calculatorTitle: String
        val windowMeasurements: String
        val windowWidth: String
        val windowHeight: String
        val wallExtension: String
        val floorClearance: String
        val fabricRequired: String
        val runningMeters: String
        val estimateBreakdown: String
        val fabricCost: String
        val sheerCost: String
        val hardwareCost: String
        val tailoringCost: String
        val installationCost: String
        val subtotal: String
        val vat: String
        val totalEstimate: String
        val requestOrder: String
        val expertTips: String
        val measuringGuideTitle: String
        val measuringGuideText: String

        // Saved Projects
        val savedTitle: String
        val noSavedDesigns: String
        val noSavedDesignsSub: String
        val createNewDesign: String
        val viewSpecs: String
        val deleteProject: String
        val shareQuotation: String
        val orderNow: String

        // Settings Screen
        val settingsTitle: String
        val languageSection: String
        val selectLanguage: String
        val currencySection: String
        val selectCurrency: String
        val unitSection: String
        val selectUnit: String
        val appearanceSection: String
        val darkMode: String
        val lightMode: String
        val systemTheme: String
        val arPreferences: String
        val showGrid: String
        val showDimensions: String
        val hapticFeedback: String
        val aboutApp: String
        val appVersion: String
        val designedForLuxury: String
        val developerName: String
        val developerRole: String
        val developerEmail: String
        val releaseDate: String
        val aboutStory: String
        val coreFeatures: String
        val copyright: String
        val viewAboutDetails: String

        // Pro Measurement Guide Dialog for AR
        val measuringDialogTitle: String
        val measuringDialogSubtitle: String
        val rule1Title: String
        val rule1Desc: String
        val rule2Title: String
        val rule2Desc: String
        val rule3Title: String
        val rule3Desc: String
        val rule4Title: String
        val rule4Desc: String
        val arTipsTitle: String
        val arTipsDesc: String
        val dontShowAgain: String
        val startDesigningNow: String
    }

    object ArabicStrings : Strings {
        override val appName = "ستائري AR"
        override val tabAR = "الواقع المعزز"
        override val tabStudio = "المصمم"
        override val tabFabrics = "الأقمشة"
        override val tabCalculator = "الحاسبة"
        override val tabProjects = "مشاريعي"
        override val tabSettings = "الإعدادات"

        override val arTitle = "تجربة الستائر بالواقع المعزز"
        override val liveCamera = "كاميرا الغرفة الحية"
        override val sampleRoom = "غرفة افتراضية جاهزة"
        override val switchRoom = "تبديل الغرفة"
        override val openCloseCurtains = "فتح وإغلاق الستائر"
        override val curtainOpen = "مفتوحة"
        override val curtainClosed = "مغلقة"
        override val adjustCorners = "ضبط أبعاد النافذة"
        override val resetCorners = "إعادة ضبط الإطار"
        override val captureSnapshot = "التقاط صورة التصميم"
        override val snapshotSaved = "تم حفظ صورة الغرفة في مشاريعك بنجاح!"
        override val lighting = "بيئة الإضاءة"
        override val daylight = "ضوء النهار"
        override val goldenHour = "غروب دافئ"
        override val nightWarm = "إضاءة ليلية"
        override val width = "العرض"
        override val height = "الارتفاع"
        override val selectColor = "لون الستارة"
        override val selectFabric = "نوع القماش"
        override val tryFabric = "تطبيق على الستارة"
        override val cameraPermissionNeeded = "يتطلب الواقع المعزز إذن الكاميرا لتصوير غرفتك الحية"
        override val grantPermission = "منح إذن الكاميرا"
        override val continueWithSample = "أو المتابعة مع غرف مسبقة التصميم"
        override val dragToPosition = "اسحب المقابض الأربعة لتطابق نافذتك تماماً"

        override val fabricsCatalogTitle = "كتالوج أقمشة الستائر الفاخرة"
        override val fabricSpecs = "مواصفات القماش"
        override val opacity = "درجة التعتيم"
        override val texture = "الملمس والمظهر"
        override val weight = "وزن القماش"
        override val composition = "التركيب والنسيج"
        override val durability = "المتانة والتحمل"
        override val care = "طريقة العناية والغسيل"
        override val lightFilter = "ترشيح الضوء والحرارة"
        override val recommendedRooms = "الغرف المناسبة"
        override val pricePerMeter = "السعر للمتر"
        override val tryInAR = "تجربة القماش في غرفتك (AR)"
        override val filterCategory = "التصنيف"
        override val searchFabric = "ابحث عن قماش أو ملمس..."
        override val allFabrics = "جميع الأقمشة"
        override val gsm = "غ/م²"

        override val customizerTitle = "مختبر تصميم الستائر"
        override val stepRoom = "الغرفة"
        override val stepFabric = "القماش"
        override val stepPleats = "طريقة الطي"
        override val stepHardware = "الإكسسوارات"
        override val pleatStyle = "نمط الطيات والكشكشة"
        override val fullnessRatio = "معامل الامتلاء والكشكشة"
        override val hardwareRod = "الماسورة والمجرى"
        override val finials = "نهايات الماسورة"
        override val tiebacks = "أربطة الستائر"
        override val smartMotor = "محرك ذكي بالتحكم عن بعد"
        override val smartMotorDesc = "محرك صامت يدعم التطبيق الذكي والريموت"
        override val includeSheer = "إضافة طبقة شيفون خفيفة (طبقتين)"
        override val includeSheerDesc = "شيفون فوال حريري للضوء النهاري الخافت"
        override val nextStep = "التالي"
        override val prevStep = "السابق"
        override val saveDesign = "حفظ التصميم"
        override val previewInAR = "معاينة بالواقع المعزز"

        override val calculatorTitle = "حاسبة المقاسات والتكلفة"
        override val windowMeasurements = "أبعاد النافذة والمساحة"
        override val windowWidth = "عرض النافذة"
        override val windowHeight = "ارتفاع النافذة"
        override val wallExtension = "امتداد الماسورة للجانبين"
        override val floorClearance = "المسافة عن الأرضية"
        override val fabricRequired = "كمية القماش المطلوبة"
        override val runningMeters = "متر طولي"
        override val estimateBreakdown = "تفاصيل عرض السعر التقديري"
        override val fabricCost = "تكلفة القماش الرئيسي"
        override val sheerCost = "تكلفة قماش الشيفون"
        override val hardwareCost = "الماسورة والمجرى والإكسسوارات"
        override val tailoringCost = "أجرة الخياطة والتفصيل الاحترافي"
        override val installationCost = "خدمة التوصيل والتركيب المنزلي"
        override val subtotal = "المجموع الفرعي"
        override val vat = "ضريبة القيمة المضافة (15%)"
        override val totalEstimate = "الإجمالي التقديري"
        override val requestOrder = "طلب عرض سعر رسمي / تفصيل"
        override val expertTips = "نصائح مهندسي الديكور"
        override val measuringGuideTitle = "كيف تقيس نافذتك مثل المحترفين؟"
        override val measuringGuideText = "1. قم بتركيب الماسورة أعلى إطار النافذة بـ 15 إلى 25 سم ليعطي الغرفة إيحاءً بالارتفاع والفخامة.\n2. احرص على مد الماسورة بـ 20-30 سم خارج جانبي النافذة حتى لا تحجب الستارة ضوء الشمس عند فتحها.\n3. معامل الكشكشة الفندقي المثالي هو x2.0 إلى x2.5 لتحصل على طيات غنية ومريحة للنظر."

        override val savedTitle = "تصاميمي المحفوظة"
        override val noSavedDesigns = "لا توجد تصاميم محفوظة بعد"
        override val noSavedDesignsSub = "ابدأ بتجربة الستائر في غرفتك بالواقع المعزز واحفظ تصاميمك هنا لمقارنتها والشراء لاحقاً."
        override val createNewDesign = "تصميم ستارة جديدة"
        override val viewSpecs = "عرض المواصفات"
        override val deleteProject = "حذف التصميم"
        override val shareQuotation = "مشاركة تفاصيل العرض"
        override val orderNow = "طلب التفصيل والشراء"

        override val settingsTitle = "الإعدادات والتفضيلات"
        override val languageSection = "اللغة (Language)"
        override val selectLanguage = "اختر لغة التطبيق"
        override val currencySection = "العملة (Currency)"
        override val selectCurrency = "اختر عملة التسعير"
        override val unitSection = "وحدات القياس (Units)"
        override val selectUnit = "وحدة قياس النوافذ"
        override val appearanceSection = "المظهر والسمة"
        override val darkMode = "الوضع الداكن الفاخر"
        override val lightMode = "الوضع الفاتح الأنيق"
        override val systemTheme = "تلقائي حسب نظام الجهاز"
        override val arPreferences = "تفضيلات الواقع المعزز (AR)"
        override val showGrid = "إظهار شبكة المساعدة والمحاذاة"
        override val showDimensions = "إظهار أبعاد النافذة أثناء المعاينة"
        override val hapticFeedback = "الاهتزاز التفاعلي عند لمس المقابض"
        override val aboutApp = "عن التطبيق والمطور"
        override val appVersion = "الإصدار 1.0.0 (Curtain AR Studio)"
        override val designedForLuxury = "صُمم لمساعدتك على اختيار وتفصيل أرقى الستائر بدقة متناهية قبل اتخاذ قرار الشراء."
        override val developerName = "NASSIM KERRAM (نسيم كرام)"
        override val developerRole = "المبتكر ومهندس التطبيق الرئيسي (Lead Developer & Creator)"
        override val developerEmail = "kerramnassim24@gmail.com"
        override val releaseDate = "نوفمبر 2026 (November 2026)"
        override val aboutStory = "تم ابتكار وتصميم تطبيق \"ستائري AR\" بواسطة المهندس نسيم كرام (NASSIM KERRAM) لتقديم تجربة رقمية استثنائية تدمج بين فنون الديكور الداخلي وأحدث تقنيات الواقع المعزز (Augmented Reality). يمكن للمستخدم تجربة واختبار مختلف أقمشة وتصاميم الستائر مباشرة في غرفته الحقيقية قبل الشراء، مع حساب المقاسات والتكاليف بشفافية واحترافية عالية."
        override val coreFeatures = "• تجربة الستائر الحية عبر الكاميرا والواقع المعزز (AR)\n• كتالوج أقمشة متنوع (المخمل، الكتان، البلاك آوت، الشيفون، القطن، البوليستر، الجاكار)\n• محاكاة واقعية ثلاثية الأبعاد لحركة وانسدال الأقمشة والإضاءة\n• حاسبة مقاسات دقيقة وعروض أسعار فورية بجميع العملات العالمية\n• دعم كامل للغتين العربية والإنجليزية"
        override val copyright = "جميع الحقوق محفوظة © 2026 NASSIM KERRAM"
        override val viewAboutDetails = "عرض بطاقة المطور والتفاصيل الكاملة"

        // Pro Measurement Guide Dialog for AR
        override val measuringDialogTitle = "دليل مهندسي الديكور لقياس النوافذ"
        override val measuringDialogSubtitle = "أسرار القياس الاحترافي لضمان تناسق مذهل للستائر في الواقع المعزز"
        override val rule1Title = "1. خدعة الارتفاع الفخم (ماسورة أعلى من النافذة)"
        override val rule1Desc = "قم بتثبيت الماسورة أعلى إطار النافذة بـ 15 إلى 25 سم (أو قريبة من السقف بـ 10 سم). هذا يعطي العين خداعاً بصرياً بأن سقف الغرفة أعلى وأكثر فخامة ورحابة."
        override val rule2Title = "2. امتداد الإضاءة الكاملة (عرض أوسع للماسورة)"
        override val rule2Desc = "احرص على مد ماسورة الستارة بمقدار 20 إلى 30 سم إضافية على كل جانب خارج إطار النافذة. هذا يتيح سحب الستائر وجمعها على الجدار دون حجب أشعة الشمس ويجعل النافذة تبدو عريضة."
        override val rule3Title = "3. نمط ملامسة الأرضية (التهدل والانسياب)"
        override val rule3Desc = "• عصري مرتفع: يرتفع 1 سم عن الأرض لسهولة التنظيف وحماية القماش.\n• ملامس كلاسيكي: يلامس الأرضية تماماً (0 سم) وهو الأكثر طلباً وتوازناً.\n• ملكي متهدل (Puddling): زيادة 5 إلى 10 سم متهدلة على الأرض للأقمشة الثقيلة كالمخمل."
        override val rule4Title = "4. معامل الكشكشة والامتلاء (Fullness Ratio)"
        override val rule4Desc = "لتحصل على طيات غنية ثلاثية الأبعاد متناسقة، اختر دائماً معامل امتلاء x2.0 أو x2.2 لستائر الموجة الحديثة (Wave)، وx2.5 للستائر الكلاسيكية المزمومة (Pinch Pleat)."
        override val arTipsTitle = "نصيحة ذهبية لتجربة الواقع المعزز (AR)"
        override val arTipsDesc = "قف على بعد 2 إلى 3 أمتار بمواجهة النافذة مباشرة وتأكد من وجود إضاءة واضحة في الغرفة، ثم حرّك المقابض الأربعة لتطابق زوايا النافذة تماماً."
        override val dontShowAgain = "عدم إظهار هذه الإرشادات تلقائياً مرة أخرى"
        override val startDesigningNow = "فهمت القواعد، ابدأ التصميم الآن"
    }

    object EnglishStrings : Strings {
        override val appName = "Curtain AR"
        override val tabAR = "AR Visualizer"
        override val tabStudio = "Studio"
        override val tabFabrics = "Fabrics"
        override val tabCalculator = "Calculator"
        override val tabProjects = "My Projects"
        override val tabSettings = "Settings"

        override val arTitle = "Curtain AR Visualizer"
        override val liveCamera = "Live Room Camera"
        override val sampleRoom = "Preset Sample Room"
        override val switchRoom = "Switch Room"
        override val openCloseCurtains = "Draw Curtains"
        override val curtainOpen = "Open"
        override val curtainClosed = "Closed"
        override val adjustCorners = "Calibrate Window Frame"
        override val resetCorners = "Reset Frame"
        override val captureSnapshot = "Capture AR Photo"
        override val snapshotSaved = "Design snapshot saved to your projects successfully!"
        override val lighting = "Lighting Atmosphere"
        override val daylight = "Daylight"
        override val goldenHour = "Golden Sunset"
        override val nightWarm = "Night Glow"
        override val width = "Width"
        override val height = "Height"
        override val selectColor = "Curtain Color"
        override val selectFabric = "Fabric Type"
        override val tryFabric = "Apply Fabric"
        override val cameraPermissionNeeded = "Camera permission is required to preview curtains in your real room"
        override val grantPermission = "Grant Camera Permission"
        override val continueWithSample = "Or continue with preset showcase rooms"
        override val dragToPosition = "Drag the 4 corner pins to match your window dimensions"

        override val fabricsCatalogTitle = "Luxury Curtain Fabrics Catalog"
        override val fabricSpecs = "Fabric Specifications"
        override val opacity = "Light Blockout / Opacity"
        override val texture = "Texture & Finish"
        override val weight = "Weight / GSM"
        override val composition = "Composition"
        override val durability = "Durability & Longevity"
        override val care = "Care Instructions"
        override val lightFilter = "Light Filtration"
        override val recommendedRooms = "Recommended Rooms"
        override val pricePerMeter = "Price per Meter"
        override val tryInAR = "Try in Your Room (AR)"
        override val filterCategory = "Category"
        override val searchFabric = "Search fabrics or textures..."
        override val allFabrics = "All Fabrics"
        override val gsm = "g/m²"

        override val customizerTitle = "Curtain Design Studio"
        override val stepRoom = "Room"
        override val stepFabric = "Fabric"
        override val stepPleats = "Pleat Style"
        override val stepHardware = "Hardware"
        override val pleatStyle = "Pleat & Heading Style"
        override val fullnessRatio = "Fabric Fullness Ratio"
        override val hardwareRod = "Curtain Rod & Track"
        override val finials = "Finial Endcaps"
        override val tiebacks = "Tiebacks & Holdbacks"
        override val smartMotor = "Smart Motorized Track"
        override val smartMotorDesc = "Silent motor with smart app & remote control"
        override val includeSheer = "Add Sheer Voile Underlayer (Dual Layer)"
        override val includeSheerDesc = "Soft flowing sheer layer for daytime filtered light"
        override val nextStep = "Next"
        override val prevStep = "Previous"
        override val saveDesign = "Save Design"
        override val previewInAR = "Preview in AR"

        override val calculatorTitle = "Measurements & Cost Estimator"
        override val windowMeasurements = "Window & Room Dimensions"
        override val windowWidth = "Window Width"
        override val windowHeight = "Window Height"
        override val wallExtension = "Rod Wall Extension"
        override val floorClearance = "Floor Clearance"
        override val fabricRequired = "Fabric Required"
        override val runningMeters = "running meters"
        override val estimateBreakdown = "Estimated Quotation Breakdown"
        override val fabricCost = "Main Fabric Cost"
        override val sheerCost = "Sheer Underlayer Cost"
        override val hardwareCost = "Rod, Track & Accessories"
        override val tailoringCost = "Bespoke Tailoring & Craftsmanship"
        override val installationCost = "Delivery & Professional Installation"
        override val subtotal = "Subtotal"
        override val vat = "VAT (15%)"
        override val totalEstimate = "Estimated Total"
        override val requestOrder = "Request Official Quotation / Order"
        override val expertTips = "Interior Designer Pro Tips"
        override val measuringGuideTitle = "How to measure windows like a pro?"
        override val measuringGuideText = "1. Mount the rod 15-25cm above the window frame to make ceilings appear higher and grander.\n2. Extend the rod 20-30cm past each side of the frame so open drapes don't block sunlight.\n3. The hotel-standard fullness ratio is 2.0x to 2.5x for deep, luxurious pleats."

        override val savedTitle = "My Saved Designs"
        override val noSavedDesigns = "No saved designs yet"
        override val noSavedDesignsSub = "Test curtains in your room with AR and save your custom designs here to compare and order."
        override val createNewDesign = "Create New Design"
        override val viewSpecs = "View Specifications"
        override val deleteProject = "Delete Design"
        override val shareQuotation = "Share Quotation"
        override val orderNow = "Order & Custom Tailor"

        override val settingsTitle = "Settings & Preferences"
        override val languageSection = "Language (اللغة)"
        override val selectLanguage = "Choose App Language"
        override val currencySection = "Currency (العملة)"
        override val selectCurrency = "Select Currency"
        override val unitSection = "Measurement Unit"
        override val selectUnit = "Choose Measurement Unit"
        override val appearanceSection = "Appearance & Theme"
        override val darkMode = "Luxury Dark Mode"
        override val lightMode = "Warm Light Mode"
        override val systemTheme = "System Default"
        override val arPreferences = "AR Visualizer Preferences"
        override val showGrid = "Show Alignment Grid"
        override val showDimensions = "Show Dimension Overlays on AR"
        override val hapticFeedback = "Haptic Vibration on Pin Dragging"
        override val aboutApp = "About Application & Creator"
        override val appVersion = "Version 1.0.0 (Curtain AR Studio)"
        override val designedForLuxury = "Designed to help you visualize, customize, and calculate luxury curtains before purchasing."
        override val developerName = "NASSIM KERRAM"
        override val developerRole = "Lead Creator & Application Architect"
        override val developerEmail = "kerramnassim24@gmail.com"
        override val releaseDate = "November 2026"
        override val aboutStory = "Curtain AR was innovated and developed by NASSIM KERRAM to bridge the gap between interior design imagination and reality. Utilizing cutting-edge Augmented Reality, it enables users to virtually style, drape, and preview luxury curtains right inside their real rooms before buying, while providing precise fabric estimations and transparent quotation calculations."
        override val coreFeatures = "• Real-time Live AR Camera & 3D Window Pinning\n• Comprehensive Fabrics Catalog (Velvet, Linen, Blackout, Sheer, Cotton, Polyester, Jacquard)\n• Physics-based 3D fabric folds and daylight lighting simulation\n• Accurate running meters calculator with multi-currency quotes\n• Full bilingual Arabic & English experience"
        override val copyright = "All rights reserved © 2026 NASSIM KERRAM"
        override val viewAboutDetails = "View Developer Card & Details"

        // Pro Measurement Guide Dialog for AR
        override val measuringDialogTitle = "Interior Designer's Window Guide"
        override val measuringDialogSubtitle = "Pro measurement secrets to ensure stunning curtain symmetry in Augmented Reality"
        override val rule1Title = "1. High & Mighty Trick (Mount Rod Higher)"
        override val rule1Desc = "Mount your curtain rod 15 to 25 cm above the window frame (or 10 cm below the ceiling). This creates a grand visual illusion that elevates ceiling height and makes the room feel much larger."
        override val rule2Title = "2. Full Daylight Extension (Extend Rod Past Frame)"
        override val rule2Desc = "Extend the rod 20 to 30 cm beyond each side of the window frame. When curtains are drawn open, they stack neatly against the wall without blocking any precious sunlight, making the window look broader."
        override val rule3Title = "3. Floor Clearance (Drape Flow & Puddling)"
        override val rule3Desc = "• Floating (1-2 cm above floor): Modern, easy to vacuum, prevents hem wear.\n• Kissing the floor (0 cm): Timeless, tailor-fit elegance.\n• Puddling (+5 to 10 cm): Luxurious pooling on the floor, ideal for heavyweight velvet."
        override val rule4Title = "4. Fullness Ratio (Rich 3D Pleats)"
        override val rule4Desc = "Always choose a fullness ratio of x2.0 to x2.2 for contemporary Wave / Ripple fold and x2.5 for Pinch Pleat to achieve lush, voluptuous folds rather than flat fabric."
        override val arTipsTitle = "Golden Tip for AR Calibration"
        override val arTipsDesc = "Stand 2 to 3 meters away facing the window directly with ample ambient room light, then drag the 4 golden pins to snap onto your window corners."
        override val dontShowAgain = "Don't show this guide automatically again"
        override val startDesigningNow = "Got it! Start Designing Now"
    }
}
