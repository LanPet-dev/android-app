package com.lanpet.core.di

import com.lanpet.data.dto.mapper.MapperRegistry
import com.lanpet.data.repository.AccountRepositoryImpl
import com.lanpet.data.repository.FreeBoardRepositoryImpl
import com.lanpet.data.repository.ProfileRepositoryImpl
import com.lanpet.data.repository.S3UploadRepositoryImpl
import com.lanpet.data.service.AccountApiService
import com.lanpet.data.service.FreeBoardApiService
import com.lanpet.data.service.ProfileApiService
import com.lanpet.data.service.S3UploadApiService
import com.lanpet.data.service.localdb.AuthDatabase
import com.lanpet.domain.repository.AccountRepository
import com.lanpet.domain.repository.FreeBoardRepository
import com.lanpet.domain.repository.ProfileRepository
import com.lanpet.domain.repository.S3UploadRepository
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
    fun provideProfileRepository(
        profileService: ProfileApiService,
        authDatabase: AuthDatabase,
        mapperRegistry: MapperRegistry,
    ): ProfileRepository =
        ProfileRepositoryImpl(
            profileService,
            authDatabase,
            mapperRegistry,
        )

    @Singleton
    @Provides
    fun provideS3UploadRepository(s3UploadApiService: S3UploadApiService): S3UploadRepository =
        S3UploadRepositoryImpl(
            s3UploadApiService,
        )
}
