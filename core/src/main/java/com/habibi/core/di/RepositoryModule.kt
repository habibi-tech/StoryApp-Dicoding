package com.habibi.core.di

import com.habibi.core.data.StoryRepository
import com.habibi.core.data.UserSessionRepository
import com.habibi.core.data.WidgetRepository
import com.habibi.core.domain.repository.IStoryRepository
import com.habibi.core.domain.repository.IUserSessionRepository
import com.habibi.core.domain.repository.IWidgetRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideStoryRepositoryModule(
        storyRepository: StoryRepository
    ): IStoryRepository

    @Binds
    abstract fun provideWidgetRepositoryModule(
        widgetRepository: WidgetRepository
    ): IWidgetRepository

    @Binds
    abstract fun provideUserSessionRepositoryModule(
        userSessionRepository: UserSessionRepository
    ): IUserSessionRepository

}