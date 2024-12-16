package com.lanpet.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModuleDev {
    @Singleton
    @Provides
    @Named("BaseApiUrl")
    fun provideBaseApiUrl(): String = "https://test.api.lanpet.co.kr/"
}
