package com.habibi.core.di

import com.habibi.core.data.UserSessionRepository
import com.habibi.core.domain.repository.IUserSessionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserSessionRepositoryModule {

    @Binds
    abstract fun provideUserSessionRepositoryModule(
        userSessionRepository: UserSessionRepository
    ): IUserSessionRepository

}