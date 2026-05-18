package com.hdapp.myapplication.di

import com.hdapp.myapplication.data.repository.LoginRepositoryImpl
import com.hdapp.myapplication.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLoginRepository(httpClient: HttpClient): LoginRepository {
        return LoginRepositoryImpl(httpClient)
    }
}
