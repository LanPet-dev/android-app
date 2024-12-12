package com.lanpet.core.di

import com.lanpet.core.manager.AuthStateHolder
import com.lanpet.data.service.AccountApiClient
import com.lanpet.data.service.AccountApiService
import com.lanpet.data.service.AuthApiClient
import com.lanpet.data.service.BaseApiService
import com.lanpet.data.service.FreeBoardApiClient
import com.lanpet.data.service.FreeBoardApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Singleton
    @Provides
    fun provideBaseApiService(
        @Named("BaseApiUrl") baseApiUrl: String
    ): BaseApiService {
        return AuthApiClient(baseApiUrl).getService()
    }

    @Singleton
    @Provides
    fun provideAuthApiClient(
        @Named("BaseApiUrl") baseApiUrl: String,
        authStateHolder: AuthStateHolder,
    ): AuthApiClient {
        return AuthApiClient(baseApiUrl)
    }

    @Singleton
    @Provides
    fun provideAuthApiService(
        authApiClient: AuthApiClient
    ): BaseApiService {
        return authApiClient.getService()
    }

    @Singleton
    @Provides
    fun provideAccountApiClient(
        @Named("BaseApiUrl") baseApiUrl: String,
        authStateHolder: AuthStateHolder,
    ): AccountApiClient {
        return AccountApiClient(baseApiUrl, authStateHolder)
    }

    @Singleton
    @Provides
    fun provideAccountApiService(
        accountApiClient: AccountApiClient
    ): AccountApiService {
        return accountApiClient.getService()
    }

    @Singleton
    @Provides
    fun provideFreeBoardApiClient(
        @Named("BaseApiUrl") baseApiUrl: String,
        authStateHolder: AuthStateHolder,
    ): FreeBoardApiClient {
        return FreeBoardApiClient(baseApiUrl, authStateHolder)
    }

    @Singleton
    @Provides
    fun provideFreeBoardApiService(
        freeBoardApiClient: FreeBoardApiClient
    ): FreeBoardApiService {
        return freeBoardApiClient.getService()
    }

    @Singleton
    @Provides
    @Named("BaseApiUrl")
    fun provideBaseApiUrl(): String {
        return "https://test.api.lanpet.co.kr/"
    }
}