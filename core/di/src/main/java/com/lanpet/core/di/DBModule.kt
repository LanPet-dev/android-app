package com.lanpet.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.lanpet.data.service.localdb.AuthDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.prefs.Preferences
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {
    private val Context.datastore by preferencesDataStore(
        name = "landing_datastore",
    )

    @Singleton
    @Provides
    fun provideAuthDatabase(
        @ApplicationContext context: Context,
    ): AuthDatabase =
        Room
            .databaseBuilder(context, AuthDatabase::class.java, "auth.db")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideLandingDataStore(
        @ApplicationContext context: Context,
    ): DataStore<androidx.datastore.preferences.core.Preferences> = context.datastore
}
