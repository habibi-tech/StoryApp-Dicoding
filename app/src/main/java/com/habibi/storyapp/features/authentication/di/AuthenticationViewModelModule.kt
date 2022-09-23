package com.habibi.storyapp.features.authentication.di

import com.habibi.core.domain.authentication.AuthenticationInteractor
import com.habibi.core.domain.authentication.usecase.IAuthenticationUseCase
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
    abstract fun provideAuthenticationUseCase(
        authenticationInteractor: AuthenticationInteractor
    ): IAuthenticationUseCase

}