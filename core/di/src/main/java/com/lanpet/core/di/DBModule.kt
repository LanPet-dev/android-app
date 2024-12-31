package com.lanpet.core.di

import android.content.Context
import androidx.room.Room
import com.lanpet.data.service.localdb.AuthDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {
    @Singleton
    @Provides
    fun provideAuthDatabase(
        @ApplicationContext context: Context,
    ): AuthDatabase =
        Room
            .databaseBuilder(context, AuthDatabase::class.java, "auth.db")
            .fallbackToDestructiveMigration()
            .build()
}
