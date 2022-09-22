package com.habibi.core.domain.repository

import com.habibi.core.data.Resource
import com.habibi.core.domain.story.data.StoryItem
import java.io.File

interface IStoryRepository {

    suspend fun getUserName(): String

    suspend fun postNewStory(photoFile: File, description: String): Resource<Unit>

    suspend fun getListStory(): Resource<List<StoryItem>>

}