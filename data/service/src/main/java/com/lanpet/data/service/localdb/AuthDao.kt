package com.lanpet.data.service.localdb

import androidx.room.Dao
import androidx.room.Query
import com.lanpet.data.dto.entity.DefaultUserProfileEntity

@Dao
interface AuthDao {
    @Query("SELECT * FROM default_user_profile")
    suspend fun getAll(): List<DefaultUserProfileEntity>

    @Query("SELECT * FROM default_user_profile WHERE accountId = :accountId")
    suspend fun getByAccountId(accountId: String): DefaultUserProfileEntity?

    @Query(" INSERT OR REPLACE INTO default_user_profile (profileId, accountId) VALUES (:profileId, :accountId) ")
    suspend fun upsert(
        profileId: String,
        accountId: String,
    )

    @Query("DELETE FROM default_user_profile WHERE accountId = :accountId")
    suspend fun deleteByAccountId(accountId: String)
}
