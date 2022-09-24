package com.habibi.core.data

import com.habibi.core.data.source.preference.UserSessionDataStore
import com.habibi.core.data.source.remote.RemoteDataSource
import com.habibi.core.data.source.remote.network.ApiResponse
import com.habibi.core.data.source.remote.response.ListStoryItem
import com.habibi.core.domain.repository.IWidgetRepository
import com.habibi.core.domain.widget.data.WidgetItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WidgetRepository @Inject constructor(
    private val userSessionDataStore: UserSessionDataStore,
    private val remoteDataSource: RemoteDataSource
): IWidgetRepository {

    override suspend fun getListStory(): Resource<List<WidgetItem>> =
    object : NetworkResource<List<WidgetItem>, List<ListStoryItem>>() {
        override suspend fun createCall(): ApiResponse<List<ListStoryItem>> =
            remoteDataSource.getListStories(userSessionDataStore.getToken())
        override suspend fun onSuccess(data: List<ListStoryItem>): List<WidgetItem> =
            data.map {
                WidgetItem(
                    it.photoUrl,
                    it.name
                )
            }
    }.result()

}