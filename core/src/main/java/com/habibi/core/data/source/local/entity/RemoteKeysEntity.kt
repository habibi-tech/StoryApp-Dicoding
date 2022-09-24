package com.habibi.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.habibi.core.data.source.local.constant.RoomConstant

@Entity(tableName = RoomConstant.TABLE_REMOTE_KEYS)
data class RemoteKeysEntity(

    @PrimaryKey
    val id: String,

    val prevKey: Int?,

    val nextKey: Int?
)
