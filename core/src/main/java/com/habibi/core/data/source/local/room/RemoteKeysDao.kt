package com.habibi.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.habibi.core.data.source.local.constant.RoomConstant
import com.habibi.core.data.source.local.entity.RemoteKeysEntity

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeysEntity>?)

    @Query("SELECT * FROM ${RoomConstant.TABLE_REMOTE_KEYS} WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): RemoteKeysEntity?

    @Query("DELETE FROM ${RoomConstant.TABLE_REMOTE_KEYS}")
    suspend fun deleteRemoteKeys()

}