package com.example.model

enum class AppLanguage(val code: String, val displayName: String, val nativeName: String) {
    ARABIC("ar", "العربية", "العربية"),
    ENGLISH("en", "English", "English")
}

enum class AppCurrency(
    val code: String,
    val symbolAr: String,
    val symbolEn: String,
    val nameAr: String,
    val nameEn: String,
    val rateFromSar: Double
) {
    DZD("DZD", "د.ج", "DZD", "دينار جزائري", "Algerian Dinar", 35.7),
    SAR("SAR", "ر.س", "SAR", "ريال سعودي", "Saudi Riyal", 1.0),
    AED("AED", "د.إ", "AED", "درهم إماراتي", "UAE Dirham", 0.98),
    KWD("KWD", "د.ك", "KWD", "دينار كويتي", "Kuwaiti Dinar", 0.082),
    QAR("QAR", "ر.ق", "QAR", "ريال قطري", "Qatari Riyal", 0.97),
    USD("USD", "$", "USD", "دولار أمريكي", "US Dollar", 0.27),
    EUR("EUR", "€", "EUR", "يورو أوروبي", "Euro", 0.25),
    EGP("EGP", "ج.م", "EGP", "جنيه مصري", "Egyptian Pound", 13.0)
}

enum class MeasurementUnit(val code: String, val labelAr: String, val labelEn: String, val toCmRatio: Float) {
    CM("cm", "سم", "cm", 1.0f),
    METER("m", "متر", "m", 100.0f),
    INCH("in", "بوصة", "in", 2.54f)
}

data class AppSettings(
    val language: AppLanguage = AppLanguage.ARABIC,
    val currency: AppCurrency = AppCurrency.DZD, // Default to DZD as requested by user!
    val unit: MeasurementUnit = MeasurementUnit.CM,
    val isDarkMode: Boolean? = null, // null = system default
    val showARGrid: Boolean = true,
    val showDimensionOverlay: Boolean = true,
    val enableHaptics: Boolean = true,
    val autoCalculateFullness: Boolean = true
)
