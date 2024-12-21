package com.lanpet.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LanPetApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
