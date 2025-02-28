package com.lanpet.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.lanpet.domain.repository.LandingRepository
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber
import javax.inject.Inject

class LandingRepositoryImpl
    @Inject
    constructor(
        private val dataStore: DataStore<Preferences>,
    ) : LandingRepository {
        private val shouldShowLandingKey = booleanPreferencesKey("should_show_landing")

        override suspend fun getShouldShowLanding(): Boolean =
            dataStore.data.firstOrNull()?.get(
                shouldShowLandingKey,
            ) ?: true

        override suspend fun saveShouldShowLanding(shouldShowLanding: Boolean): Boolean {
            try {
                dataStore.edit { preferences ->
                    preferences[shouldShowLandingKey] = shouldShowLanding
                }

                return true
            } catch (e: Exception) {
                Timber.e(e)
                return false
            }
        }
    }
