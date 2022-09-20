package com.habibi.core.domain.story

import com.habibi.core.data.Resource
import com.habibi.core.domain.repository.IStoryRepository
import com.habibi.core.domain.repository.IUserSessionRepository
import com.habibi.core.domain.story.data.StoryItem
import com.habibi.core.domain.story.usecase.IStoryListUseCase
import com.habibi.core.domain.story.usecase.IStoryUseCase
import javax.inject.Inject

class StoryListInteractor @Inject constructor(
    private val storyRepository: IStoryRepository
): IStoryListUseCase {

    override suspend fun getListStory(): Resource<List<StoryItem>> =
        storyRepository.getListStory()

}