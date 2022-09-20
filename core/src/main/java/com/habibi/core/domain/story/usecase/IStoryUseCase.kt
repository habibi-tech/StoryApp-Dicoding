package com.habibi.core.domain.story.usecase

import com.habibi.core.data.Resource
import com.habibi.core.domain.story.data.StoryItem
import okhttp3.MultipartBody

interface IStoryUseCase {

    suspend fun setUserLogout()

    suspend fun getUserName(): String

}