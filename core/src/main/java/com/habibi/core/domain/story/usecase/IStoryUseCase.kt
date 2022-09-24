package com.habibi.core.domain.story.usecase

import androidx.paging.PagingData
import com.habibi.core.data.Resource
import com.habibi.core.data.source.local.entity.StoriesEntity
import com.habibi.core.domain.story.data.StoryItem
import java.io.File
import kotlinx.coroutines.flow.Flow

interface IStoryUseCase {

    suspend fun setUserLogout()

    suspend fun getUserName(): String

    suspend fun getListStory(): Resource<List<StoryItem>>

    suspend fun postNewStory(photoFile: File, description: String): Resource<Unit>

    fun getStoryPaging() : Flow<PagingData<StoriesEntity>>

}