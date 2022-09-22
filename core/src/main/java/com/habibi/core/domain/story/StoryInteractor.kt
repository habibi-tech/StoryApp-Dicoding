package com.habibi.core.domain.story

import com.habibi.core.domain.repository.IUserSessionRepository
import com.habibi.core.domain.story.usecase.IStoryUseCase
import javax.inject.Inject

class StoryInteractor @Inject constructor(
    private val userSessionRepository: IUserSessionRepository
): IStoryUseCase {

    override suspend fun setUserLogout() {
        userSessionRepository.setUserLogout()
    }

    override suspend fun getUserName(): String =
        userSessionRepository.getUserName()

}