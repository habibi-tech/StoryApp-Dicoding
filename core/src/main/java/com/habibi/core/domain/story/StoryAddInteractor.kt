package com.habibi.core.domain.story

import com.habibi.core.data.Resource
import com.habibi.core.domain.repository.IStoryRepository
import com.habibi.core.domain.story.usecase.IStoryAddUseCase
import javax.inject.Inject
import okhttp3.MultipartBody

class StoryAddInteractor @Inject constructor(
    private val storyRepository: IStoryRepository
): IStoryAddUseCase {

    override suspend fun postNewStory(
        photoPath: String,
        description: String
    ): Resource<Unit> {

        val photo = MultipartBody.Part.createFormData(
            name = "photo",
            value = photoPath
        )

        return storyRepository.postNewStory(photo, description)
    }

}