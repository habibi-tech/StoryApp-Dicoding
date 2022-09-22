package com.habibi.core.domain.story.usecase

interface IStoryUseCase {

    suspend fun setUserLogout()

    suspend fun getUserName(): String

}