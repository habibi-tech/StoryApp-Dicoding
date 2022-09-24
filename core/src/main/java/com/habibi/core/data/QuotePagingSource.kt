package com.habibi.core.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.habibi.core.data.source.preference.UserSessionDataStore
import com.habibi.core.data.source.remote.network.ApiService
import com.habibi.core.domain.story.data.StoryItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuotePagingSource @Inject constructor(
    private val userSessionDataStore: UserSessionDataStore,
    private val apiService: ApiService
) : PagingSource<Int, StoryItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getListStories(
                mapOf("Authorization" to "Bearer ${userSessionDataStore.getToken()}"),
                page,
                params.loadSize
            )

            val listData = responseData.body()?.listStory?.map {
                StoryItem(
                    id = it.id,
                    photoUrl = it.photoUrl,
                    name = it.name,
                    description = it.description
                )
            }

            LoadResult.Page(
                data = listData ?: emptyList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.body()?.listStory.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, StoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val INITIAL_PAGE_INDEX = 1
    }
}