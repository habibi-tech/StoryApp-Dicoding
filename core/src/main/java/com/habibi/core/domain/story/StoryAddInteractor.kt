package com.habibi.core.domain.story

import com.habibi.core.data.Resource
import com.habibi.core.domain.repository.IStoryRepository
import com.habibi.core.domain.story.usecase.IStoryAddUseCase
import java.io.File
import javax.inject.Inject

class StoryAddInteractor @Inject constructor(
    private val storyRepository: IStoryRepository
): IStoryAddUseCase {

    override suspend fun postNewStory(
        photoFile: File,
        description: String
    ): Resource<Unit> {
        return storyRepository.postNewStory(photoFile, description)
    }

}