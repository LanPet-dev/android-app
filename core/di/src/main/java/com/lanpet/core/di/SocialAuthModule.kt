package com.lanpet.core.di

import com.lanpet.core.manager.AuthStateHolder
import com.lanpet.data.repository.AuthRepositoryImpl
import com.lanpet.data.service.AuthService
import com.lanpet.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideAuthService(
        @Named("AuthUrl") baseUrl: String,
    ): AuthService =
        Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideAuthRepository(authService: AuthService): AuthRepository = AuthRepositoryImpl(authService)

    @Provides
    @Singleton
    @Named("AuthUrl")
    fun provideBaseUrl(): String = "https://lanpet.auth.ap-northeast-2.amazoncognito.com/"

    @Provides
    @Singleton
    fun provideAuthStateHolder(): AuthStateHolder = AuthStateHolder()
}
