package com.example.marvelapp.framework.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.data.DbConstants

@Entity(tableName = DbConstants.REMOTE_KEYS_TABLE_NAME)
data class RemoteKey(
    @PrimaryKey
    val id: Int = 0,

    @ColumnInfo(name = DbConstants.REMOTE_KEYS_COLUMN_OFFSET)
    val nextOffset: Int?
)