package com.hdapp.myapplication

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
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
            LoginScreen(viewModel = viewModel, onLoginSuccess = {})
        }

        composeTestRule.onNodeWithTag(TestTags.LOGIN_LOGO).assertExists()
        composeTestRule.onNodeWithTag(TestTags.LOGIN_BUTTON).assertExists()
    }

    @Test
    fun testLoginSuccess() {
        val viewModel = LoginViewModel(LoginUseCase(FakeLoginRepository()))
        var loginSuccessCalled = false
        
        composeTestRule.setContent {
            LoginScreen(viewModel = viewModel, onLoginSuccess = { loginSuccessCalled = true })
        }

        composeTestRule.onNodeWithTag(TestTags.LOGIN_USERNAME_FIELD).performTextInput("testuser")
        composeTestRule.onNodeWithTag(TestTags.LOGIN_PASSWORD_FIELD).performTextInput("password")
        composeTestRule.onNodeWithTag(TestTags.LOGIN_BUTTON).performClick()
        
        composeTestRule.waitForIdle()
        assert(loginSuccessCalled)
    }
}
