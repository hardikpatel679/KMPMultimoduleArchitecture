package com.hdapp.myapplication.di

import com.hdapp.myapplication.core.AppEnvironment
import com.hdapp.myapplication.core.BuildContext
import com.hdapp.myapplication.data.repository.LoginRepositoryImpl
import com.hdapp.myapplication.data.repository.MockLoginRepositoryImpl
import com.hdapp.myapplication.data.repository.ProductRepositoryImpl
import com.hdapp.myapplication.domain.repository.LoginRepository
import com.hdapp.myapplication.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideLoginRepository(httpClient: HttpClient, json: Json): LoginRepository {
        return if (BuildContext.environment == AppEnvironment.MOCK) {
            MockLoginRepositoryImpl(json)
        } else {
            LoginRepositoryImpl(httpClient)
        }
    }

    @Provides
    @Singleton
    fun provideProductRepository(httpClient: HttpClient): ProductRepository {
        return ProductRepositoryImpl(httpClient)
    }
}
