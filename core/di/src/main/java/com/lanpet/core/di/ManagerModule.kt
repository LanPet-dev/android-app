package com.lanpet.core.di

import android.content.Context
import com.lanpet.core.manager.CoilManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ManagerModule {
    @Singleton
    @Provides
    fun provideCoilManager(
        @ApplicationContext context: Context,
    ): CoilManager = CoilManager(context)
}
