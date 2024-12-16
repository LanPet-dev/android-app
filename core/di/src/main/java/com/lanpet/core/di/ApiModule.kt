package com.lanpet.core.di

import com.lanpet.core.manager.AuthStateHolder
import com.lanpet.data.service.AccountApiClient
import com.lanpet.data.service.AccountApiService
import com.lanpet.data.service.FreeBoardApiClient
import com.lanpet.data.service.FreeBoardApiService
import com.lanpet.data.service.ProfileApiClient
import com.lanpet.data.service.ProfileApiService
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
    fun provideProfileApiService(
        @Named("BaseApiUrl") baseApiUrl: String,
        authStateHolder: AuthStateHolder,
    ): ProfileApiService =
        ProfileApiClient(
            baseApiUrl,
            authStateHolder,
        ).getService()

    @Singleton
    @Provides
    fun provideAuthApiClient(
        @Named("BaseApiUrl") baseApiUrl: String,
        authStateHolder: AuthStateHolder,
    ): ProfileApiClient = ProfileApiClient(baseApiUrl, authStateHolder)

    @Singleton
    @Provides
    fun provideAuthApiService(authApiClient: ProfileApiClient): ProfileApiService = authApiClient.getService()

    @Singleton
    @Provides
    fun provideAccountApiClient(
        @Named("BaseApiUrl") baseApiUrl: String,
        authStateHolder: AuthStateHolder,
    ): AccountApiClient = AccountApiClient(baseApiUrl, authStateHolder)

    @Singleton
    @Provides
    fun provideAccountApiService(accountApiClient: AccountApiClient): AccountApiService = accountApiClient.getService()

    @Singleton
    @Provides
    fun provideFreeBoardApiClient(
        @Named("BaseApiUrl") baseApiUrl: String,
        authStateHolder: AuthStateHolder,
    ): FreeBoardApiClient = FreeBoardApiClient(baseApiUrl, authStateHolder)

    @Singleton
    @Provides
    fun provideFreeBoardApiService(freeBoardApiClient: FreeBoardApiClient): FreeBoardApiService = freeBoardApiClient.getService()
}
