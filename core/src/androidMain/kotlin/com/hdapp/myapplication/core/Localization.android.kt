package com.hdapp.myapplication.core

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

@Composable
actual fun LanguageEffect(languageCode: String) {
    val context = LocalContext.current
    LaunchedEffect(languageCode) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}
