package com.lanpet.app

import android.app.Application
import com.lanpet.core.auth.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class LanPetApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG || BuildConfig.BUILD_TYPE.equals("dev")) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
