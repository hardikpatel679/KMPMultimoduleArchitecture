package com.hdapp.myapplication.core

import androidx.compose.runtime.*
import platform.Foundation.NSUserDefaults
import platform.Foundation.setValue

@Composable
actual fun LanguageEffect(languageCode: String) {
    LaunchedEffect(languageCode) {
        val userDefaults = NSUserDefaults.standardUserDefaults
        userDefaults.setValue(listOf(languageCode), "AppleLanguages")
        userDefaults.synchronize()
    }
}
