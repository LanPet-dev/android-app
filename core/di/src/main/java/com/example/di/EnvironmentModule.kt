package com.example.di

import com.lanpet.auth.BuildConfig
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object EnvironmentModule {

    @Named("GOOGLE_CLIENT_ID")
    fun provideGoogleClientId(
    ): String {
        return BuildConfig.GOOGLE_OAUTH_CLIENT_KEY
    }

}