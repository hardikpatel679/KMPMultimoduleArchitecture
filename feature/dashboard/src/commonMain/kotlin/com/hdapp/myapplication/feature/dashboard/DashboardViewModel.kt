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
            DashboardIntent.LoadProducts -> {
                loadProducts()
            }
            DashboardIntent.LoadMoreProducts -> {
                loadMoreProducts()
            }
            is DashboardIntent.SearchProducts -> {
                _state.update { it.copy(searchQuery = intent.query) }
            }
        }
    }

    private fun loadProducts() {
        if (_state.value.products.isNotEmpty()) return
        
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, hasMore = true) }
            val result = getProductsUseCase(limit = 10, skip = 0)
            result.onSuccess { products ->
                val categories = products.map { it.category }.distinct()
                _state.update { 
                    it.copy(
                        products = products,
                        categories = categories,
                        isLoading = false,
                        hasMore = products.size >= 10
                    ) 
                }
            }.onFailure {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun loadMoreProducts() {
        if (_state.value.isLoadingMore || !_state.value.hasMore) return

        viewModelScope.launch {
            _state.update { it.copy(isLoadingMore = true) }
            val currentProductsCount = _state.value.products.size
            val result = getProductsUseCase(limit = 10, skip = currentProductsCount)
            result.onSuccess { newProducts ->
                val allProducts = _state.value.products + newProducts
                val categories = allProducts.map { it.category }.distinct()
                _state.update { 
                    it.copy(
                        products = allProducts,
                        categories = categories,
                        isLoadingMore = false,
                        hasMore = newProducts.size >= 10
                    ) 
                }
            }.onFailure {
                _state.update { it.copy(isLoadingMore = false) }
            }
        }
    }
}
