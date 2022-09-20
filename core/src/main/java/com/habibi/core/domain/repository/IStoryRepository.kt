package com.habibi.core.domain.repository

import com.habibi.core.data.Resource
import com.habibi.core.domain.story.data.StoryItem
import okhttp3.MultipartBody

interface IStoryRepository {

    suspend fun getUserName(): String

    suspend fun postNewStory(photo: MultipartBody.Part, description: String): Resource<Unit>

    suspend fun getListStory(): Resource<List<StoryItem>>

}