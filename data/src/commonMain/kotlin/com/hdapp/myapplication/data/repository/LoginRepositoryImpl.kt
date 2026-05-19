package com.hdapp.myapplication.data.repository

import com.hdapp.myapplication.core.NetworkConstants
import com.hdapp.myapplication.core.safeApiCall
import com.hdapp.myapplication.data.model.LoginRequest
import com.hdapp.myapplication.data.model.RemoteLoginResponse
import com.hdapp.myapplication.data.model.toDomain
import com.hdapp.myapplication.domain.model.User
import com.hdapp.myapplication.domain.repository.LoginRepository
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

class LoginRepositoryImpl(
    private val httpClient: HttpClient
) : LoginRepository {
    private val json = Json { ignoreUnknownKeys = true; isLenient = true }

    override suspend fun login(username: String, password: String): Result<User> {
        println("LoginRepositoryImpl: Login START for $username")
        return safeApiCall {
            println("LoginRepositoryImpl: Executing POST...")
            val response = httpClient.post(NetworkConstants.LOGIN_ENDPOINT) {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(username = username, password = password))
            }
            
            println("LoginRepositoryImpl: Response Status = ${response.status}")
            val bodyString = response.bodyAsText()
            println("LoginRepositoryImpl: Raw Body = $bodyString")
            
            val remoteResponse = json.decodeFromString<RemoteLoginResponse>(bodyString)
            println("LoginRepositoryImpl: Parsing successful")

            remoteResponse.toDomain()
        }
    }
}
