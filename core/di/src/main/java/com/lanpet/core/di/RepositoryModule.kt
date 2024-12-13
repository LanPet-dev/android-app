package com.lanpet.core.di

import com.lanpet.domain.repository.AccountRepository
import com.lanpet.data.repository.AccountRepositoryImpl
import com.lanpet.data.repository.FreeBoardRepositoryImpl
import com.lanpet.data.service.AccountApiService
import com.lanpet.data.service.FreeBoardApiService
import com.lanpet.domain.repository.FreeBoardRepository
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

    @Singleton
    @Provides
    fun provideFreeBoardRepository(
        freeBoardService: FreeBoardApiService
    ): FreeBoardRepository {
        return FreeBoardRepositoryImpl(
            freeBoardService
        )
    }
}