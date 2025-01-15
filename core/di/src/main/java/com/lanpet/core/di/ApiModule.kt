package com.lanpet.core.di

import com.lanpet.core.manager.AuthStateHolder
import com.lanpet.data.service.AccountApiClient
import com.lanpet.data.service.AccountApiService
import com.lanpet.data.service.FreeBoardApiClient
import com.lanpet.data.service.FreeBoardApiService
import com.lanpet.data.service.ProfileApiClient
import com.lanpet.data.service.ProfileApiService
import com.lanpet.data.service.S3UploadApiClient
import com.lanpet.data.service.S3UploadApiService
import com.lanpet.data.service.interceptors.RefreshTokenInterceptor
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
    fun provideAuthApiClient(
        @Named("BaseApiUrl") baseApiUrl: String,
        authStateHolder: AuthStateHolder,
        refreshTokenInterceptor: RefreshTokenInterceptor,
    ): ProfileApiClient = ProfileApiClient(baseApiUrl, authStateHolder, refreshTokenInterceptor)

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
        refreshTokenInterceptor: RefreshTokenInterceptor,
    ): FreeBoardApiClient = FreeBoardApiClient(baseApiUrl, authStateHolder, refreshTokenInterceptor)

    @Singleton
    @Provides
    fun provideFreeBoardApiService(freeBoardApiClient: FreeBoardApiClient): FreeBoardApiService = freeBoardApiClient.getService()

    @Singleton
    @Provides
    @Named("FreeBoardBaseApiUrl")
    fun provideFreeBoardApiUrl(
        @Named("BaseApiUrl") baseUrl: String,
    ): String = baseUrl + "sarangbangs/"

    @Singleton
    @Provides
    fun provideS3UploadApiClient(authStateHolder: AuthStateHolder): S3UploadApiClient = S3UploadApiClient(authStateHolder)

    @Singleton
    @Provides
    fun provideS3UploadApiService(s3UploadApiClient: S3UploadApiClient): S3UploadApiService = s3UploadApiClient.getService()
}
