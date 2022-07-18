package com.example.marvelapp.framework.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.data.DbConstants.REMOTE_KEYS_COLUMN_INFO_OFFSET
import com.example.core.data.DbConstants.REMOTE_KEYS_TABLE_NAME

@Entity(tableName = REMOTE_KEYS_TABLE_NAME)
data class RemoteKeyEntity(
    @PrimaryKey
    val id: Int = 0,
    @ColumnInfo(name = REMOTE_KEYS_COLUMN_INFO_OFFSET)
    val nextOffset: Int?
)
