package com.hdapp.myapplication.feature.login

import app.cash.turbine.test
import com.hdapp.myapplication.core.NetworkError
import com.hdapp.myapplication.domain.model.User
import com.hdapp.myapplication.domain.repository.LoginRepository
import com.hdapp.myapplication.domain.usecase.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: LoginViewModel
    private lateinit var loginUseCase: LoginUseCase

    private class FakeLoginRepository(var result: Result<User>) : LoginRepository {
        override suspend fun login(username: String, password: String): Result<User> {
            return result
        }
    }

    private lateinit var fakeRepository: FakeLoginRepository

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        val user = User(1, "test", "test@test.com", "First", "Last", "male", "image", "token")
        fakeRepository = FakeLoginRepository(Result.success(user))
        loginUseCase = LoginUseCase(fakeRepository)
        viewModel = LoginViewModel(loginUseCase)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() {
        val state = viewModel.state.value
        assertNull(state.user)
        assertNull(state.error)
        assertTrue(!state.isLoading)
    }

    @Test
    fun `login success updates state correctly`() = runTest {
        val user = User(1, "test", "test@test.com", "First", "Last", "male", "image", "token")
        fakeRepository.result = Result.success(user)

        viewModel.state.test {
            assertEquals(LoginState(), awaitItem()) // Initial state

            viewModel.onIntent(LoginIntent.Login("user", "pass"))
            
            assertEquals(LoginState(isLoading = true), awaitItem())
            assertEquals(LoginState(isLoading = false, user = user), awaitItem())
        }
    }

    @Test
    fun `login success updates state and emits effect`() = runTest {
        val user = User(1, "test", "test@test.com", "First", "Last", "male", "image", "token")
        fakeRepository.result = Result.success(user)

        viewModel.effect.test {
            viewModel.onIntent(LoginIntent.Login("user", "pass"))
            assertEquals(LoginEffect.NavigateToHome, awaitItem())
        }
    }

    @Test
    fun `login failure updates state and emits error effect`() = runTest {
        val networkError = NetworkError.Unauthorized()
        fakeRepository.result = Result.failure(networkError)

        viewModel.effect.test {
            viewModel.onIntent(LoginIntent.Login("user", "pass"))
            assertEquals(LoginEffect.ShowSnackbar(networkError), awaitItem())
        }
    }
}
