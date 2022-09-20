package com.habibi.core.domain.story.usecase

import com.habibi.core.data.Resource

interface IStoryAddUseCase {

    suspend fun postNewStory(photoPath: String, description: String): Resource<Unit>

}