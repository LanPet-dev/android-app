package com.lanpet.core.di

import com.example.repository.AccountRepository
import com.lanpet.AccountRepositoryImpl
import com.lanpet.service.AccountApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideAccountRepository(
        accountService: AccountApiService
    ): AccountRepository {
        return AccountRepositoryImpl(
            accountService
        )
    }
}