package com.hdapp.myapplication.feature.dashboard

data class DashboardState(
    val isArabic: Boolean = false
)

sealed interface DashboardIntent {
    data object ToggleLanguage : DashboardIntent
    data object Logout : DashboardIntent
}

sealed interface DashboardEffect {
    data object NavigateToLogin : DashboardEffect
}
