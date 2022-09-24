package com.habibi.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.habibi.core.data.source.local.entity.RemoteKeysEntity
import com.habibi.core.data.source.local.entity.StoriesEntity

@Database(
    entities = [StoriesEntity::class, RemoteKeysEntity::class],
    version = 1,
    exportSchema = false
)
abstract class StoryDatabase: RoomDatabase() {
    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}