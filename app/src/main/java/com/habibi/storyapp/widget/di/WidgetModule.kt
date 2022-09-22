package com.habibi.storyapp.widget.di

import com.habibi.core.domain.widget.WidgetInteractor
import com.habibi.core.domain.widget.usecase.IWidgetUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WidgetModule {

    @Binds
    abstract fun provideWidgetStoryListUseCase(
        widgetInteractor: WidgetInteractor
    ): IWidgetUseCase

}