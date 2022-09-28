package com.habibi.storyapp.features.story.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.habibi.core.domain.story.usecase.IStoryUseCase
import com.habibi.storyapp.features.story.presentation.dummy.StoryDataDummy
import com.habibi.storyapp.features.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class StoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var useCase : IStoryUseCase

    private lateinit var viewModel: StoryViewModel

    @Before
    fun setUp() {
        viewModel = StoryViewModel(useCase)
    }

    @Test
    fun `get username then return username`() = runTest {
        val expected = StoryDataDummy.name

        Mockito.`when`(useCase.getUserName()).thenReturn(expected)
        viewModel.getUserName().join()

        val result = viewModel.userName
        assertEquals(expected, result)
    }

    @Test
    fun `call set not logged in then only call once`() = runTest {
        viewModel.setUserNotLogin()

        Mockito.verify(useCase).setUserLogout()
    }

}