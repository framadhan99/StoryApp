package com.fajar.submissionstoryapp.core.data.resource.local.room

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface KeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<KeyEntity>)


    @Query("SELECT * FROM keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): KeyEntity?


    @Query("DELETE FROM keys")
    suspend fun deleteKeys()
}