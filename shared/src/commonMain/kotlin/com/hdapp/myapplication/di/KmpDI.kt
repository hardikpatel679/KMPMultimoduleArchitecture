package com.hdapp.myapplication.di

import com.hdapp.myapplication.core.AppEnvironment
import com.hdapp.myapplication.core.BuildContext
import com.hdapp.myapplication.core.createHttpClient
import com.hdapp.myapplication.data.repository.LoginRepositoryImpl
import com.hdapp.myapplication.data.repository.MockLoginRepositoryImpl
import com.hdapp.myapplication.data.repository.ProductRepositoryImpl
import com.hdapp.myapplication.domain.repository.LoginRepository
import com.hdapp.myapplication.domain.repository.ProductRepository
import com.hdapp.myapplication.domain.usecase.GetProductsUseCase
import com.hdapp.myapplication.domain.usecase.LoginUseCase
import com.hdapp.myapplication.feature.dashboard.DashboardViewModel
import com.hdapp.myapplication.feature.login.LoginViewModel
import io.ktor.client.*
import kotlinx.serialization.json.Json

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

    private val json = Json { ignoreUnknownKeys = true }

    val loginRepository: LoginRepository by lazy {
        if (BuildContext.environment == AppEnvironment.MOCK) {
            MockLoginRepositoryImpl(json)
        } else {
            LoginRepositoryImpl(httpClient)
        }
    }

    val loginUseCase: LoginUseCase by lazy {
        LoginUseCase(loginRepository)
    }

    val productRepository: ProductRepository by lazy {
        ProductRepositoryImpl(httpClient)
    }

    val getProductsUseCase: GetProductsUseCase by lazy {
        GetProductsUseCase(productRepository)
    }

    fun createLoginViewModel() = LoginViewModel(loginUseCase)
    fun createDashboardViewModel() = DashboardViewModel(getProductsUseCase)
}
