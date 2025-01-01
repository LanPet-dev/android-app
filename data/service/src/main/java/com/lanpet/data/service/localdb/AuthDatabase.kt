package com.lanpet.data.service.localdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lanpet.data.dto.entity.DefaultUserProfileEntity

@Database(entities = [DefaultUserProfileEntity::class], version = 1)
abstract class AuthDatabase : RoomDatabase() {
    abstract fun authDao(): AuthDao
}
