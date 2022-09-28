package com.habibi.storyapp.features.story.presentation.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.habibi.core.data.source.local.entity.StoriesEntity
import com.habibi.core.domain.story.usecase.IStoryUseCase
import com.habibi.storyapp.features.story.presentation.dummy.StoryDataDummy
import com.habibi.storyapp.features.story.presentation.list.adapter.StoryListAdapter
import com.habibi.storyapp.features.utils.MainDispatcherRule
import com.habibi.storyapp.features.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class StoryListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var useCase : IStoryUseCase

    private lateinit var viewModel: StoryListViewModel

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }

    @Test
    fun `when get story then return not null`() = runTest {
        val dummyStory = StoryDataDummy.generateDummyStoryResponse()
        val data: PagingData<StoriesEntity> = StoryPagingSource.snapshot(dummyStory)
        val expected = flowOf(data)

        Mockito.`when`(useCase.getStoryPaging()).thenReturn(expected)
        viewModel = StoryListViewModel(useCase)
        val actualQuote: PagingData<StoriesEntity> = viewModel.getListPaging.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryListAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)

        assertNotNull(differ.snapshot())
        assertEquals(dummyStory, differ.snapshot())
        assertEquals(dummyStory.size, differ.snapshot().size)
        assertEquals(dummyStory[0].id, differ.snapshot()[0]?.id)
    }

    @Test
    fun `when get story then return empty`() = runTest {
        val dummyStory = StoryDataDummy.generateDummyEmptyStoryResponse()
        val data: PagingData<StoriesEntity> = StoryPagingSource.snapshot(dummyStory)
        val expected = flowOf(data)

        Mockito.`when`(useCase.getStoryPaging()).thenReturn(expected)
        viewModel = StoryListViewModel(useCase)
        val actualQuote: PagingData<StoriesEntity> = viewModel.getListPaging.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryListAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)

        assertNotNull(differ.snapshot())
        assertEquals(dummyStory, differ.snapshot())
        assertEquals(dummyStory.size, differ.snapshot().size)
        assertTrue(dummyStory.isEmpty())
    }

}