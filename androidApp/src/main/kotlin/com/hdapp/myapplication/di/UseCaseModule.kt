package com.hdapp.myapplication.di

import com.hdapp.myapplication.domain.repository.LoginRepository
import com.hdapp.myapplication.domain.repository.ProductRepository
import com.hdapp.myapplication.domain.usecase.GetProductsUseCase
import com.hdapp.myapplication.domain.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: LoginRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetProductsUseCase(repository: ProductRepository): GetProductsUseCase {
        return GetProductsUseCase(repository)
    }
}
