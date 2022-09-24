package com.habibi.core.di

import android.content.Context
import androidx.room.Room
import com.habibi.core.data.source.local.room.RemoteKeysDao
import com.habibi.core.data.source.local.room.StoryDao
import com.habibi.core.data.source.local.room.StoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun provideStoryDao(
        storyDatabase: StoryDatabase
    ): StoryDao {
        return storyDatabase.storyDao()
    }

    @Singleton
    @Provides
    fun provideRemoteKeysDao(
        storyDatabase: StoryDatabase
    ): RemoteKeysDao {
        return storyDatabase.remoteKeysDao()
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): StoryDatabase {
        return Room.databaseBuilder(
            context,
            StoryDatabase::class.java, "Story.db"
        ).fallbackToDestructiveMigration().build()
    }

}