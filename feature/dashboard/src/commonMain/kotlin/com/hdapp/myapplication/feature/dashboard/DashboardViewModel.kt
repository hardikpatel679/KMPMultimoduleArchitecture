package com.hdapp.myapplication.feature.dashboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

open class DashboardViewModel : ViewModel() {
    private val _state = MutableStateFlow(DashboardState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<DashboardEffect>(extraBufferCapacity = 1)
    val effect = _effect.asSharedFlow()

    fun onIntent(intent: DashboardIntent) {
        when (intent) {
            DashboardIntent.ToggleLanguage -> {
                _state.update { it.copy(isArabic = !it.isArabic) }
            }
            DashboardIntent.Logout -> {
                _effect.tryEmit(DashboardEffect.NavigateToLogin)
            }
        }
    }
}
