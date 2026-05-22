package com.hdapp.myapplication.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hdapp.myapplication.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class DashboardViewModel(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(DashboardState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<DashboardEffect>(extraBufferCapacity = 1)
    val effect = _effect.asSharedFlow()

    init {
        onIntent(DashboardIntent.FetchProducts)
    }

    fun onIntent(intent: DashboardIntent) {
        when (intent) {
            DashboardIntent.ToggleLanguage -> {
                _state.update { it.copy(isArabic = !it.isArabic) }
            }
            DashboardIntent.Logout -> {
                _effect.tryEmit(DashboardEffect.NavigateToLogin)
            }
            is DashboardIntent.SelectTab -> {
                _state.update { it.copy(selectedTab = intent.tab) }
            }
            is DashboardIntent.SelectCategory -> {
                _state.update { it.copy(selectedCategory = intent.category) }
            }
            DashboardIntent.FetchProducts -> fetchProducts()
        }
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            getProductsUseCase().onSuccess { products ->
                val categories = products.map { it.category }.distinct()
                _state.update { 
                    it.copy(
                        isLoading = false, 
                        products = products, 
                        categories = categories,
                        selectedCategory = categories.firstOrNull()
                    ) 
                }
            }.onFailure { error ->
                _state.update { it.copy(isLoading = false, error = error.message) }
            }
        }
    }
}
