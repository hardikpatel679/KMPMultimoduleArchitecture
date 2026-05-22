package com.hdapp.myapplication.di

import com.hdapp.myapplication.core.AppBuildContext
import com.hdapp.myapplication.core.AppEnvironment
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
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

/**
 * Koin modules for the shared logic.
 */

val networkModule = module {
    single { createHttpClient() }
    single { Json { ignoreUnknownKeys = true } }
}

val repositoryModule = module {
    single<LoginRepository> {
        if (AppBuildContext.environment == AppEnvironment.MOCK) {
            MockLoginRepositoryImpl(get())
        } else {
            LoginRepositoryImpl(get())
        }
    }
    single<ProductRepository> { ProductRepositoryImpl(get()) }
}

val useCaseModule = module {
    factoryOf(::LoginUseCase)
    factoryOf(::GetProductsUseCase)
}

val viewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::DashboardViewModel)
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            networkModule,
            repositoryModule,
            useCaseModule,
            viewModelModule
        )
    }

// For iOS usage
fun initKoin() = initKoin {}
