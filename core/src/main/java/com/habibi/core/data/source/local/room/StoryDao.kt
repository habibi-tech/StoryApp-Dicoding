package com.habibi.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.habibi.core.data.source.local.constant.RoomConstant
import com.habibi.core.data.source.local.entity.StoriesEntity

@Dao
interface StoryDao {

    @Query("SELECT * FROM ${RoomConstant.TABLE_STORIES}")
    fun getAllStories(): PagingSource<Int, StoriesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListStories(list: List<StoriesEntity>?)

    @Query("DELETE FROM ${RoomConstant.TABLE_STORIES}")
    suspend fun deleteListStories()

}