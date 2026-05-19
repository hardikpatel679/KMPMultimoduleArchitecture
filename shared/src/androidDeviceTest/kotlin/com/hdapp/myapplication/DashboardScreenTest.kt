package com.hdapp.myapplication

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.v2.createComposeRule
import com.hdapp.myapplication.core.ProvideAppStrings
import com.hdapp.myapplication.core.TestTags
import com.hdapp.myapplication.domain.model.Product
import com.hdapp.myapplication.domain.repository.ProductRepository
import com.hdapp.myapplication.domain.usecase.GetProductsUseCase
import com.hdapp.myapplication.feature.dashboard.DashboardScreen
import com.hdapp.myapplication.feature.dashboard.DashboardViewModel
import org.junit.Rule
import org.junit.Test

class DashboardScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private class FakeProductRepository : ProductRepository {
        override suspend fun getProducts(limit: Int, skip: Int): Result<List<Product>> {
            return Result.success(listOf(
                Product(1, "Product 1", "Description 1", 10.0, "", "Category 1"),
                Product(2, "Product 2", "Description 2", 20.0, "", "Category 2")
            ))
        }
    }

    @Test
    fun testDashboardElementsExist() {
        val viewModel = DashboardViewModel(GetProductsUseCase(FakeProductRepository()))
        
        composeTestRule.setContent {
            ProvideAppStrings(isArabic = false) {
                DashboardScreen(viewModel = viewModel, onLogout = {})
            }
        }

        // Initially we should see the product list
        composeTestRule.onNodeWithTag(TestTags.DASHBOARD_PRODUCT_LIST).assertExists()
        composeTestRule.onNodeWithTag(TestTags.DASHBOARD_LANGUAGE_SWITCH).assertExists()
    }

    @Test
    fun testToggleLanguage() {
        val viewModel = DashboardViewModel(GetProductsUseCase(FakeProductRepository()))
        
        composeTestRule.setContent {
            ProvideAppStrings(isArabic = false) {
                DashboardScreen(viewModel = viewModel, onLogout = {})
            }
        }

        composeTestRule.onNodeWithTag(TestTags.DASHBOARD_LANGUAGE_SWITCH).performClick()
        
        // Check if switch state updated
        composeTestRule.onNodeWithTag(TestTags.DASHBOARD_LANGUAGE_SWITCH).assertIsOn()
    }

    @Test
    fun testSearchFieldAppears() {
        val viewModel = DashboardViewModel(GetProductsUseCase(FakeProductRepository()))
        
        composeTestRule.setContent {
            ProvideAppStrings(isArabic = false) {
                DashboardScreen(viewModel = viewModel, onLogout = {})
            }
        }

        composeTestRule.onNodeWithContentDescription("Open Search").performClick()
        composeTestRule.onNodeWithTag(TestTags.DASHBOARD_SEARCH_FIELD).assertExists()
    }
}
