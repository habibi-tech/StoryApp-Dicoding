package com.habibi.storyapp.features.story.presentation.list

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.habibi.core.data.source.local.entity.StoriesEntity

class StoryPagingSource : PagingSource<Int, LiveData<List<StoriesEntity>>>() {
    companion object {
        fun snapshot(items: List<StoriesEntity>): PagingData<StoriesEntity> {
            return PagingData.from(items)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, LiveData<List<StoriesEntity>>>): Int {
        return 0
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<StoriesEntity>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}