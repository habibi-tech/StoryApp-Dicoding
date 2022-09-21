package com.habibi.core.domain.story.usecase

import com.habibi.core.data.Resource
import java.io.File

interface IStoryAddUseCase {

    suspend fun postNewStory(photoFile: File, description: String): Resource<Unit>

}