package com.hdapp.myapplication.di

import com.hdapp.myapplication.core.createHttpClient
import com.hdapp.myapplication.data.repository.LoginRepositoryImpl
import com.hdapp.myapplication.domain.repository.LoginRepository
import com.hdapp.myapplication.domain.usecase.LoginUseCase
import io.ktor.client.*

/**
 * A simple Dependency Injection container for Kotlin Multiplatform.
 * This can be used from iOS (Swift) to access shared logic and dependencies.
 *
 * On Android, Hilt is used for dependency injection, but it uses the same
 * underlying factory functions and classes.
 */
object KmpDI {
    val httpClient: HttpClient by lazy {
        createHttpClient()
    }

    val loginRepository: LoginRepository by lazy {
        LoginRepositoryImpl(httpClient)
    }

    val loginUseCase: LoginUseCase by lazy {
        LoginUseCase(loginRepository)
    }
}
