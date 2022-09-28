package com.habibi.storyapp.features.story.presentation.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.habibi.core.data.Resource
import com.habibi.core.domain.story.data.StoryItem
import com.habibi.core.domain.story.usecase.IStoryUseCase
import com.habibi.storyapp.features.story.presentation.dummy.StoryDataDummy
import com.habibi.storyapp.features.utils.MainDispatcherRule
import com.habibi.storyapp.features.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class StoryMapsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var useCase : IStoryUseCase

    private lateinit var viewModel: StoryMapsViewModel

    @Before
    fun setUp() {
        viewModel = StoryMapsViewModel(useCase)
    }

    @Test
    fun `get list story then success`() = runTest {
        val data = StoryDataDummy.listStory
        val message = StoryDataDummy.message
        val expected = Resource.Success(data, message)

        Mockito.`when`(useCase.getListStoryWithLocation()).thenReturn(expected)
        viewModel.getListStoryWithLocation().join()

        val result = viewModel.listStoryWithLocation.getOrAwaitValue()
        assertTrue(result is Resource.Success)
        assertEquals(expected, result)
    }

    @Test
    fun `get list story then failed`() = runTest {
        val message = StoryDataDummy.message
        val expected = Resource.Failed<List<StoryItem>>(message)

        Mockito.`when`(useCase.getListStoryWithLocation()).thenReturn(expected)
        viewModel.getListStoryWithLocation().join()

        val result = viewModel.listStoryWithLocation.getOrAwaitValue()
        assertTrue(result is Resource.Failed)
        assertEquals(expected, result)
    }

    @Test
    fun `get list story then error`() = runTest {
        val message = StoryDataDummy.messageResource
        val expected = Resource.Error<List<StoryItem>>(message)

        Mockito.`when`(useCase.getListStoryWithLocation()).thenReturn(expected)
        viewModel.getListStoryWithLocation().join()

        val result = viewModel.listStoryWithLocation.getOrAwaitValue()
        assertTrue(result is Resource.Error)
        assertEquals(expected, result)
    }
}