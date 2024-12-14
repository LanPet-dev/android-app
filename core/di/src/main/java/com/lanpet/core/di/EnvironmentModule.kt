package com.lanpet.core.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EnvironmentModule {
    @Singleton
    @Named("GOOGLE_CLIENT_ID")
    fun provideGoogleClientId(): String = BuildConfig.GOOGLE_OAUTH_CLIENT_KEY
}
