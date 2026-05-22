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
    val error: String? = null
) {
    val filteredProducts: List<Product>
        get() = if (selectedCategory == null) products else products.filter { it.category == selectedCategory }
}

sealed interface DashboardIntent {
    data object ToggleLanguage : DashboardIntent
    data object Logout : DashboardIntent
    data class SelectTab(val tab: DashboardTab) : DashboardIntent
    data class SelectCategory(val category: String?) : DashboardIntent
    data object FetchProducts : DashboardIntent
}

sealed interface DashboardEffect {
    data object NavigateToLogin : DashboardEffect
}
