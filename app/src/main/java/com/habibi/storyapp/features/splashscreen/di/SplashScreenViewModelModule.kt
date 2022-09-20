package com.habibi.storyapp.features.splashscreen.di

import com.habibi.core.domain.splashscreen.usecase.ISplashScreenUseCase
import com.habibi.core.domain.splashscreen.SplashScreenInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class SplashScreenViewModelModule {

    @Binds
    @ViewModelScoped
    abstract fun provideSplashScreenUseCase(
        splashScreenInteractor: SplashScreenInteractor
    ): ISplashScreenUseCase

}