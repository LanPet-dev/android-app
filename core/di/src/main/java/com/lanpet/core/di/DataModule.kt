package com.lanpet.core.di

import com.lanpet.data.dto.mapper.ButlerDtoToDomainButlerMapper
import com.lanpet.data.dto.mapper.DefaultMapperRegistry
import com.lanpet.data.dto.mapper.DomainButlerToButlerDtoMapper
import com.lanpet.data.dto.mapper.DomainPetToPetDtoMapper
import com.lanpet.data.dto.mapper.MapperRegistry
import com.lanpet.data.dto.mapper.PetDtoToDomainPetMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideMapperRegistry(): MapperRegistry =
        DefaultMapperRegistry().apply {
            register(PetDtoToDomainPetMapper())
            register(DomainPetToPetDtoMapper())
            register(ButlerDtoToDomainButlerMapper())
            register(DomainButlerToButlerDtoMapper())
        }
}
