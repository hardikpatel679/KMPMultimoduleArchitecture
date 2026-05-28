package com.hdapp.myapplication.data.repository

import com.hdapp.myapplication.core.NetworkConstants
import com.hdapp.myapplication.core.safeApiCall
import com.hdapp.myapplication.data.model.LoginRequest
import com.hdapp.myapplication.data.model.RemoteLoginResponse
import com.hdapp.myapplication.data.model.toDomain
import com.hdapp.myapplication.domain.model.User
import com.hdapp.myapplication.domain.repository.LoginRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*

class LoginRepositoryImpl(
    private val httpClient: HttpClient
) : LoginRepository {
    override suspend fun login(username: String, password: String): Result<User> {
        println("LoginRepositoryImpl: Attempting login for $username")
        return safeApiCall {
            println("LoginRepositoryImpl: Inside safeApiCall, calling post to ${NetworkConstants.LOGIN_ENDPOINT}")
            val response = httpClient.post(NetworkConstants.LOGIN_ENDPOINT) {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(username = username, password = password))
                timeout {
                    requestTimeoutMillis = 60000
                    connectTimeoutMillis = 60000
                    socketTimeoutMillis = 60000
                }
            }
            println("LoginRepositoryImpl: Received response with status ${response.status}")
            val remoteResponse: RemoteLoginResponse = response.body()
            println("LoginRepositoryImpl: Parsed RemoteLoginResponse successfully")
            remoteResponse.toDomain()
        }
    }
}
