package com.hdapp.myapplication

import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.hdapp.myapplication.core.TestTags
import com.hdapp.myapplication.feature.dashboard.DashboardScreen
import com.hdapp.myapplication.feature.dashboard.DashboardViewModel
import org.junit.Rule
import org.junit.Test

class DashboardScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDashboardElementsExist() {
        val viewModel = DashboardViewModel()
        
        composeTestRule.setContent {
            DashboardScreen(viewModel = viewModel, onLogout = {})
        }

        composeTestRule.onNodeWithTag(TestTags.DASHBOARD_WELCOME_TEXT).assertExists()
        composeTestRule.onNodeWithTag(TestTags.DASHBOARD_LOGOUT_BUTTON).assertExists()
        composeTestRule.onNodeWithTag(TestTags.DASHBOARD_LANGUAGE_SWITCH).assertExists()
    }

    @Test
    fun testToggleLanguage() {
        val viewModel = DashboardViewModel()
        
        composeTestRule.setContent {
            DashboardScreen(viewModel = viewModel, onLogout = {})
        }

        composeTestRule.onNodeWithTag(TestTags.DASHBOARD_LANGUAGE_SWITCH).performClick()
        
        // Check if state updated
        composeTestRule.onNodeWithTag(TestTags.DASHBOARD_LANGUAGE_SWITCH).assertIsOn()
    }

    @Test
    fun testLogoutButtonClick() {
        val viewModel = DashboardViewModel()
        var logoutClicked = false
        
        composeTestRule.setContent {
            DashboardScreen(viewModel = viewModel, onLogout = { logoutClicked = true })
        }

        composeTestRule.onNodeWithTag(TestTags.DASHBOARD_LOGOUT_BUTTON).performClick()
        
        assert(logoutClicked)
    }
}
