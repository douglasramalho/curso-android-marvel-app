package com.example.marvelapp.framework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.DbConstants.REMOTE_KEYS_TABLE_NAME
import com.example.marvelapp.framework.db.entity.RemoteKeyEntity

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKeyEntity)

    @Query("SELECT * FROM $REMOTE_KEYS_TABLE_NAME")
    suspend fun remoteKey(): RemoteKeyEntity

    @Query("DELETE FROM $REMOTE_KEYS_TABLE_NAME")
    suspend fun clearAll()
}