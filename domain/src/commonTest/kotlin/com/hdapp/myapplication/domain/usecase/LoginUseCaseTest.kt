package com.hdapp.myapplication.domain.usecase

import com.hdapp.myapplication.domain.model.User
import com.hdapp.myapplication.domain.repository.LoginRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LoginUseCaseTest {

    private class FakeLoginRepository(private val result: Result<User>) : LoginRepository {
        override suspend fun login(username: String, password: String): Result<User> {
            return result
        }
    }

    @Test
    fun `invoke returns success when repository returns success`() = runTest {
        val user = User(1, "test", "test@test.com", "First", "Last", "male", "image", "token")
        val useCase = LoginUseCase(FakeLoginRepository(Result.success(user)))

        val result = useCase("username", "password")

        assertTrue(result.isSuccess)
        assertEquals(user, result.getOrNull())
    }

    @Test
    fun `invoke returns failure when repository returns failure`() = runTest {
        val exception = Exception("Login failed")
        val useCase = LoginUseCase(FakeLoginRepository(Result.failure(exception)))

        val result = useCase("username", "password")

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
