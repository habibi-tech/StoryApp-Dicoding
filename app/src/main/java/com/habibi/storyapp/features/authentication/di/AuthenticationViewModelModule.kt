package com.habibi.storyapp.features.authentication.di

import com.habibi.core.domain.authentication.LoginInteractor
import com.habibi.core.domain.authentication.RegisterInteractor
import com.habibi.core.domain.authentication.usecase.ILoginUseCase
import com.habibi.core.domain.authentication.usecase.IRegisterUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthenticationViewModelModule {

    @Binds
    @ViewModelScoped
    abstract fun provideLoginUseCase(
        loginInteractor: LoginInteractor
    ): ILoginUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideRegisterUseCase(
        registerInteractor: RegisterInteractor
    ): IRegisterUseCase

}