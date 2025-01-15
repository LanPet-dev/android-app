package com.lanpet.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

// TODO("Satoshi"): replace with production endpoint
@Module
@InstallIn(SingletonComponent::class)
object ApiModuleProd {
    @Singleton
    @Provides
    @Named("BaseApiUrl")
    fun provideBaseApiUrl(): String = "https://test.api.lanpet.co.kr/"

    @Singleton
    @Provides
    @Named("FreeBoardApiUrl")
    fun provideFreeBoardApiUrl(
        @Named("BaseApiUrl") baseUrl: String,
    ): String = baseUrl + "sarangbangs/"
}
