package com.hdapp.myapplication

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.hdapp.myapplication.core.ProvideAppStrings
import com.hdapp.myapplication.core.TestTags
import com.hdapp.myapplication.domain.model.User
import com.hdapp.myapplication.domain.repository.LoginRepository
import com.hdapp.myapplication.domain.usecase.LoginUseCase
import com.hdapp.myapplication.feature.login.LoginScreen
import com.hdapp.myapplication.feature.login.LoginViewModel
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private class FakeLoginRepository : LoginRepository {
        override suspend fun login(username: String, password: String): Result<User> {
            return Result.success(User(1, "test", "test@test.com", "First", "Last", "male", "image", "token"))
        }
    }

    @Test
    fun testLoginButtonExists() {
        val viewModel = LoginViewModel(LoginUseCase(FakeLoginRepository()))
        
        composeTestRule.setContent {
            ProvideAppStrings(isArabic = false) {
                LoginScreen(viewModel = viewModel, onLoginSuccess = {})
            }
        }

        composeTestRule.onNodeWithTag(TestTags.LOGIN_LOGO).assertExists()
        composeTestRule.onNodeWithTag(TestTags.LOGIN_BUTTON).assertExists()
    }

    @Test
    fun testLoginSuccess() {
        val viewModel = LoginViewModel(LoginUseCase(FakeLoginRepository()))
        var loginSuccessCalled = false
        
        composeTestRule.setContent {
            ProvideAppStrings(isArabic = false) {
                LoginScreen(viewModel = viewModel, onLoginSuccess = { loginSuccessCalled = true })
            }
        }

        composeTestRule.onNodeWithTag(TestTags.LOGIN_USERNAME_FIELD).performTextInput("testuser")
        composeTestRule.onNodeWithTag(TestTags.LOGIN_PASSWORD_FIELD).performTextInput("password")
        composeTestRule.onNodeWithTag(TestTags.LOGIN_BUTTON).performClick()
        
        composeTestRule.waitForIdle()
        assert(loginSuccessCalled)
    }

    @Test
    fun testLanguageConsistency() {
        val viewModel = LoginViewModel(LoginUseCase(FakeLoginRepository()))
        
        composeTestRule.setContent {
            ProvideAppStrings(isArabic = true) { // Test with Arabic
                LoginScreen(viewModel = viewModel, onLoginSuccess = {})
            }
        }

        // Verify that Username and Password labels are still in English as requested
        composeTestRule.onNodeWithText("Username").assertExists()
        composeTestRule.onNodeWithText("Password").assertExists()
    }
}
