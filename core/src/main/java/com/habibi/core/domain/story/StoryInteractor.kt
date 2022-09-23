package com.habibi.core.domain.story

import com.habibi.core.data.Resource
import com.habibi.core.domain.repository.IStoryRepository
import com.habibi.core.domain.repository.IUserSessionRepository
import com.habibi.core.domain.story.data.StoryItem
import com.habibi.core.domain.story.usecase.IStoryUseCase
import java.io.File
import javax.inject.Inject

class StoryInteractor @Inject constructor(
    private val storyRepository: IStoryRepository,
    private val userSessionRepository: IUserSessionRepository
): IStoryUseCase {

    override suspend fun setUserLogout() {
        userSessionRepository.setUserLogout()
    }

    override suspend fun getUserName(): String =
        userSessionRepository.getUserName()

    override suspend fun getListStory(): Resource<List<StoryItem>> =
        storyRepository.getListStory()

    override suspend fun postNewStory(
        photoFile: File,
        description: String
    ): Resource<Unit> {
        return storyRepository.postNewStory(photoFile, description)
    }

}