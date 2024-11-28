package com.lanpet.core.di

import com.example.repository.AuthRepository
import com.lanpet.AuthRepositoryImpl
import com.lanpet.core.auth.AuthStateHolder
import com.lanpet.service.AuthService
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
    fun provideAuthService(@Named("BaseUrl") baseUrl: String): AuthService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }

    @Provides
    fun provideAuthRepository(
        authService: AuthService
    ): AuthRepository {
        return AuthRepositoryImpl(authService)
    }

    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl(): String {
        return "https://lanpet.auth.ap-northeast-2.amazoncognito.com/"
    }

    @Provides
    @Singleton
    fun provideAuthStateHolder(): AuthStateHolder {
        return AuthStateHolder()
    }
}
