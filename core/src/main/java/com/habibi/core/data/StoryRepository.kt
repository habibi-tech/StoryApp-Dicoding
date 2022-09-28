package com.habibi.core.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.habibi.core.data.source.local.entity.StoriesEntity
import com.habibi.core.data.source.local.room.StoryDatabase
import com.habibi.core.data.source.preference.UserSessionDataStore
import com.habibi.core.data.source.remote.RemoteDataSource
import com.habibi.core.data.source.remote.network.ApiResponse
import com.habibi.core.data.source.remote.response.ListStoryItem
import com.habibi.core.domain.repository.IStoryRepository
import com.habibi.core.domain.story.data.StoryItem
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class StoryRepository @Inject constructor(
    private val userSessionDataStore: UserSessionDataStore,
    private val remoteDataSource: RemoteDataSource,
    private val storyRemoteMediator: StoryRemoteMediator,
    private val database: StoryDatabase
): IStoryRepository {

    override suspend fun postNewStory(photoFile: File, description: String, latitude: Float?, longitude: Float?): Resource<Unit> =
        object : NetworkResource<Unit, Unit>() {
            override suspend fun createCall(): ApiResponse<Unit> {
                return remoteDataSource.postNewStory(userSessionDataStore.getToken(), photoFile, description, latitude, longitude)
            }
            override suspend fun onSuccess(data: Unit) {}
        }.result()

    override suspend fun getListStoryWithLocation(): Resource<List<StoryItem>> =
        object : NetworkResource<List<StoryItem>, List<ListStoryItem>>() {
            override suspend fun createCall(): ApiResponse<List<ListStoryItem>> =
                remoteDataSource.getListStories(userSessionDataStore.getToken(), 1)
            override suspend fun onSuccess(data: List<ListStoryItem>): List<StoryItem> =
                data.map {
                    StoryItem(
                        id = it.id,
                        photoUrl = it.photoUrl,
                        name = it.name,
                        description = it.description,
                        lat = it.lat,
                        lon = it.lon
                    )
                }
        }.result()

    override fun getStoryPaging() : Flow<PagingData<StoriesEntity>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE
            ),
            remoteMediator = storyRemoteMediator,
            pagingSourceFactory = {
                database.storyDao().getAllStories()
            }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}