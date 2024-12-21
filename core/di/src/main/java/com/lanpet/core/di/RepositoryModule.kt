package com.lanpet.core.di

import com.lanpet.data.repository.AccountRepositoryImpl
import com.lanpet.data.repository.FreeBoardRepositoryImpl
import com.lanpet.data.repository.ProfileRepositoryImpl
import com.lanpet.data.service.AccountApiService
import com.lanpet.data.service.FreeBoardApiService
import com.lanpet.data.service.ProfileApiService
import com.lanpet.domain.repository.AccountRepository
import com.lanpet.domain.repository.FreeBoardRepository
import com.lanpet.domain.repository.ProfileRepository
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
    fun provideAccountRepository(accountService: AccountApiService): AccountRepository =
        AccountRepositoryImpl(
            accountService,
        )

    @Singleton
    @Provides
    fun provideFreeBoardRepository(freeBoardService: FreeBoardApiService): FreeBoardRepository =
        FreeBoardRepositoryImpl(
            freeBoardService,
        )

    @Singleton
    @Provides
    fun provideProfileRepository(profileService: ProfileApiService): ProfileRepository =
        ProfileRepositoryImpl(
            profileService,
        )
}
