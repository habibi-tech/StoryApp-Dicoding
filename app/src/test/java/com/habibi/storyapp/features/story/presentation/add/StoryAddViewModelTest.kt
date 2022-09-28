package com.habibi.storyapp.features.story.presentation.add

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.habibi.core.data.Resource
import com.habibi.core.domain.story.usecase.IStoryUseCase
import com.habibi.storyapp.features.story.presentation.dummy.StoryDataDummy
import com.habibi.storyapp.features.utils.MainDispatcherRule
import com.habibi.storyapp.features.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class StoryAddViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var useCase : IStoryUseCase

    private lateinit var viewModel: StoryAddViewModel

    @Before
    fun setUp() {
        viewModel = StoryAddViewModel(useCase)
    }

    @Test
    fun  `set current photo path then return same value`() {
        val expected = StoryDataDummy.photoPath

        viewModel.setCurrentPhotoPath(expected)

        val result = viewModel.currentPhotoPath
        assertEquals(expected, result)
    }

    @Test
    fun  `set current latitude then return same value`() {
        val expected = StoryDataDummy.latitude

        viewModel.setCurrentLatitude(expected)

        val result = viewModel.currentLatitude
        assertEquals(expected, result)
    }

    @Test
    fun  `set current longitude then return same value`() {
        val expected = StoryDataDummy.longitude

        viewModel.setCurrentLongitude(expected)

        val result = viewModel.currentLongitude
        assertEquals(expected, result)
    }

    @Test
    fun  `check validation then return valid`() {
        val file = StoryDataDummy.file
        val validDescription = StoryDataDummy.validDescription
        val validDescriptionError = StoryDataDummy.validDescriptionError
        val expected = true

        viewModel.checkFieldValidation(
            file = file,
            descriptionError = validDescriptionError,
            description = validDescription
        )

        val result = viewModel.fieldValid.getOrAwaitValue()
        assertEquals(expected, result)
    }

    @Test
    fun  `check validation then return invalid`() {
        val file = null
        val invalidDescription = StoryDataDummy.invalidDescription
        val invalidDescriptionError = StoryDataDummy.invalidDescriptionError
        val expected = false

        viewModel.checkFieldValidation(
            file = file,
            descriptionError = invalidDescriptionError,
            description = invalidDescription
        )

        val result = viewModel.fieldValid.getOrAwaitValue()
        assertEquals(expected, result)
    }

    @Test
    fun `submit story then success`() = runTest {
        val file = StoryDataDummy.file
        val validDescription = StoryDataDummy.validDescription
        val validDescriptionError = StoryDataDummy.validDescriptionError
        val description = StoryDataDummy.validDescription
        val latitude = StoryDataDummy.latitude
        val longitude = StoryDataDummy.longitude
        val message = StoryDataDummy.message
        val expected = Resource.Success(Unit, message)

        viewModel.setCurrentLatitude(latitude)
        viewModel.setCurrentLatitude(longitude)
        viewModel.checkFieldValidation(
            file = file,
            descriptionError = validDescriptionError,
            description = validDescription
        )
        Mockito.`when`(useCase.postNewStory(viewModel.photoFile.value!!, description, viewModel.currentLatitude, viewModel.currentLongitude))
            .thenReturn(expected)
        viewModel.postNewStory(description).join()

        val result = viewModel.newStory.getOrAwaitValue()
        assertTrue(result is Resource.Success)
        assertEquals(expected, result)
    }

    @Test
    fun `submit story then failed`() = runTest {
        val file = StoryDataDummy.file
        val validDescription = StoryDataDummy.validDescription
        val validDescriptionError = StoryDataDummy.validDescriptionError
        val description = StoryDataDummy.validDescription
        val latitude = StoryDataDummy.latitude
        val longitude = StoryDataDummy.longitude
        val message = StoryDataDummy.message
        val expected = Resource.Failed<Unit>(message)

        viewModel.setCurrentLatitude(latitude)
        viewModel.setCurrentLatitude(longitude)
        viewModel.checkFieldValidation(
            file = file,
            descriptionError = validDescriptionError,
            description = validDescription
        )
        Mockito.`when`(useCase.postNewStory(viewModel.photoFile.value!!, description, viewModel.currentLatitude, viewModel.currentLongitude))
            .thenReturn(expected)
        viewModel.postNewStory(description).join()

        val result = viewModel.newStory.getOrAwaitValue()
        assertTrue(result is Resource.Failed)
        assertEquals(expected, result)
    }

    @Test
    fun `submit story then error`() = runTest {
        val file = StoryDataDummy.file
        val validDescription = StoryDataDummy.validDescription
        val validDescriptionError = StoryDataDummy.validDescriptionError
        val description = StoryDataDummy.validDescription
        val latitude = StoryDataDummy.latitude
        val longitude = StoryDataDummy.longitude
        val message = StoryDataDummy.messageResource
        val expected = Resource.Error<Unit>(message)

        viewModel.setCurrentLatitude(latitude)
        viewModel.setCurrentLatitude(longitude)
        viewModel.checkFieldValidation(
            file = file,
            descriptionError = validDescriptionError,
            description = validDescription
        )
        Mockito.`when`(useCase.postNewStory(viewModel.photoFile.value!!, description, viewModel.currentLatitude, viewModel.currentLongitude))
            .thenReturn(expected)
        viewModel.postNewStory(description).join()

        val result = viewModel.newStory.getOrAwaitValue()
        assertTrue(result is Resource.Error)
        assertEquals(expected, result)
    }

}