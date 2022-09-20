package com.habibi.core.domain.story.usecase

import com.habibi.core.data.Resource
import com.habibi.core.domain.story.data.StoryItem

interface IStoryListUseCase {

    suspend fun getListStory(): Resource<List<StoryItem>>

}