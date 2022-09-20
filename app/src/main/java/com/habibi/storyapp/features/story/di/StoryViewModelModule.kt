package com.habibi.storyapp.features.story.di

import com.habibi.core.domain.story.StoryAddInteractor
import com.habibi.core.domain.story.StoryInteractor
import com.habibi.core.domain.story.StoryListInteractor
import com.habibi.core.domain.story.usecase.IStoryAddUseCase
import com.habibi.core.domain.story.usecase.IStoryListUseCase
import com.habibi.core.domain.story.usecase.IStoryUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class StoryViewModelModule {

    @Binds
    @ViewModelScoped
    abstract fun provideStoryUseCase(
        storyInteractor: StoryInteractor
    ): IStoryUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideStoryListUseCase(
        storyListInteractor: StoryListInteractor
    ): IStoryListUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideStoryAddUseCase(
        storyAddInteractor: StoryAddInteractor
    ): IStoryAddUseCase

}