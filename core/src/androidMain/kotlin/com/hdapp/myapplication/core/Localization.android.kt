package com.hdapp.myapplication.core

import androidx.compose.runtime.*

@Composable
actual fun LanguageEffect(languageCode: String) {
    // Disabled manual configuration update to prevent Activity restarts and state loss.
    // Compose Multiplatform handles LTR/RTL via LocalLayoutDirection which is already set in AppLocalization.
    /*
    val context = LocalContext.current
    LaunchedEffect(languageCode) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        if (config.locales[0].language != languageCode) {
            config.setLocale(locale)
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }
    }
    */
}
