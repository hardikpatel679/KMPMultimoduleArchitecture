package com.hdapp.myapplication.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Main Hilt Module for the application.
 *
 * In Hilt, modules are automatically discovered based on the @InstallIn annotation.
 * This AppModule serves as a central point for global application-level dependencies
 * or as a conceptual aggregator for the dependency graph.
 *
 * Currently, dependencies are organized into:
 * - NetworkModule (in :data module)
 * - RepositoryModule (in :data module)
 * - UseCaseModule (in :domain module)
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // Add any global application-wide dependencies here if they don't fit
    // into specific modules like Network, Repository, or UseCase.
}
