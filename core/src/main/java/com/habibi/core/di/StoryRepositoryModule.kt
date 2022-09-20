package com.habibi.core.di

import com.habibi.core.data.StoryRepository
import com.habibi.core.domain.repository.IStoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StoryRepositoryModule {

    @Binds
    abstract fun provideStoryRepositoryModule(
        storyRepository: StoryRepository
    ): IStoryRepository

}