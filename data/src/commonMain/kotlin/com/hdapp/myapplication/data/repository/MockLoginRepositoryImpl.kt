package com.hdapp.myapplication.data.repository

import com.hdapp.myapplication.data.model.RemoteLoginResponse
import com.hdapp.myapplication.data.model.toDomain
import com.hdapp.myapplication.domain.model.User
import com.hdapp.myapplication.domain.repository.LoginRepository
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import myapplication.core.generated.resources.Res

class MockLoginRepositoryImpl(
    private val json: Json
) : LoginRepository {
    override suspend fun login(username: String, password: String): Result<User> {
        delay(1000) // Simulate network delay
        
        return try {
            val bytes = Res.readBytes("files/login_success.json")
            val jsonString = bytes.decodeToString()
            val remoteResponse = json.decodeFromString<RemoteLoginResponse>(jsonString)
            Result.success(remoteResponse.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
