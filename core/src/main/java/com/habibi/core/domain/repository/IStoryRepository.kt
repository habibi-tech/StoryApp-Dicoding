package com.habibi.core.domain.repository

import androidx.paging.PagingData
import com.habibi.core.data.Resource
import com.habibi.core.data.source.local.entity.StoriesEntity
import com.habibi.core.domain.story.data.StoryItem
import java.io.File
import kotlinx.coroutines.flow.Flow

interface IStoryRepository {

    suspend fun getUserName(): String

    suspend fun postNewStory(photoFile: File, description: String, latitude: Float?, longitude: Float?): Resource<Unit>

    suspend fun getListStoryWithLocation(): Resource<List<StoryItem>>

    fun getStoryPaging() : Flow<PagingData<StoriesEntity>>

}