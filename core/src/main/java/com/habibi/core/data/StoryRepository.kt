package com.habibi.core.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.habibi.core.data.source.preference.UserSessionDataStore
import com.habibi.core.data.source.remote.RemoteDataSource
import com.habibi.core.data.source.remote.network.ApiResponse
import com.habibi.core.data.source.remote.response.ListStoryItem
import com.habibi.core.domain.repository.IStoryRepository
import com.habibi.core.domain.story.data.StoryItem
import com.habibi.core.utils.convertToMultiPart
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody.Companion.toRequestBody

@Singleton
class StoryRepository @Inject constructor(
    private val userSessionDataStore: UserSessionDataStore,
    private val remoteDataSource: RemoteDataSource,
    private val quotePagingSource: QuotePagingSource
): IStoryRepository {

    override suspend fun getUserName(): String =
        userSessionDataStore.getUserName()

    override suspend fun postNewStory(photoFile: File, description: String): Resource<Unit> =
        object : NetworkResource<Unit, Unit>() {
            override suspend fun createCall(): ApiResponse<Unit> {

                val multipartBody = photoFile.convertToMultiPart()
                val requestBodyDesc = description.toRequestBody()

                return remoteDataSource.postNewStory(userSessionDataStore.getToken(), multipartBody, requestBodyDesc)
            }
            override suspend fun onSuccess(data: Unit) {}
        }.result()

    override suspend fun getListStory(): Resource<List<StoryItem>> =
        object : NetworkResource<List<StoryItem>, List<ListStoryItem>>() {
            override suspend fun createCall(): ApiResponse<List<ListStoryItem>> =
                remoteDataSource.getListStories(userSessionDataStore.getToken())
            override suspend fun onSuccess(data: List<ListStoryItem>): List<StoryItem> =
                data.map {
                    StoryItem(
                        it.photoUrl,
                        it.name,
                        it.description
                    )
                }
        }.result()

    override fun getStoryPaging() : Flow<PagingData<StoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = { quotePagingSource }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}