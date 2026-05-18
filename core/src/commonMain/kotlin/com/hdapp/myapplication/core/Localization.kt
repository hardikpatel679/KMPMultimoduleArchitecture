package com.hdapp.myapplication.core

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

/**
 * Provides a central way to manage app language and RTL/LTR directions.
 */
@Composable
fun AppLocalization(
    isArabic: Boolean,
    content: @Composable () -> Unit
) {
    val layoutDirection = if (isArabic) LayoutDirection.Rtl else LayoutDirection.Ltr
    val languageCode = if (isArabic) "ar" else "en"
    
    // Platform-specific language override
    LanguageEffect(languageCode)

    CompositionLocalProvider(
        LocalLayoutDirection provides layoutDirection,
    ) {
        content()
    }
}

/**
 * Platform-specific effect to apply the language override.
 */
@Composable
expect fun LanguageEffect(languageCode: String)
