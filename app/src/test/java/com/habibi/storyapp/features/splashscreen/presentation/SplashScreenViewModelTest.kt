package com.habibi.storyapp.features.splashscreen.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.habibi.core.domain.splashscreen.usecase.ISplashScreenUseCase
import com.habibi.storyapp.features.utils.MainDispatcherRule
import com.habibi.storyapp.features.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class SplashScreenViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var useCase : ISplashScreenUseCase

    private lateinit var viewModel: SplashScreenViewModel

    @Test
    fun `get logged in status then not logged in`() = runTest {
        val expected = false

        Mockito.`when`(useCase.isLoggedIn()).thenReturn(expected)
        viewModel = SplashScreenViewModel(useCase)

        val result = viewModel.isLoggedIn.getOrAwaitValue()
        assertEquals(expected, result)
    }

    @Test
    fun `get logged in status then logged in`() = runTest {
        val expected = true

        Mockito.`when`(useCase.isLoggedIn()).thenReturn(expected)
        viewModel = SplashScreenViewModel(useCase)

        val result = viewModel.isLoggedIn.getOrAwaitValue()
        assertEquals(expected, result)
    }

}