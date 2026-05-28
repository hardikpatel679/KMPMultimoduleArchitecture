package com.hdapp.myapplication.feature.dashboard

import com.hdapp.myapplication.domain.model.Product

enum class DashboardTab {
    Product, Wealth, Service, Cart
}

data class DashboardState(
    val isArabic: Boolean = false,
    val selectedTab: DashboardTab = DashboardTab.Product,
    val products: List<Product> = emptyList(),
    val categories: List<String> = emptyList(),
    val selectedCategory: String? = null,
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val hasMore: Boolean = true,
    val searchQuery: String = ""
)

sealed interface DashboardIntent {
    data object ToggleLanguage : DashboardIntent
    data object Logout : DashboardIntent
    data class SelectTab(val tab: DashboardTab) : DashboardIntent
    data class SelectCategory(val category: String?) : DashboardIntent
    data class SearchProducts(val query: String) : DashboardIntent
    data object LoadProducts : DashboardIntent
    data object LoadMoreProducts : DashboardIntent
}

sealed interface DashboardEffect {
    data object NavigateToLogin : DashboardEffect
}
