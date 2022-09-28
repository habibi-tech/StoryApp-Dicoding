package com.habibi.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.habibi.core.data.source.local.entity.StoriesEntity
import com.habibi.core.data.source.local.room.StoryDatabase
import com.habibi.core.data.source.preference.UserSessionDataStore
import com.habibi.core.data.source.remote.RemoteDataSource
import com.habibi.core.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
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
class StoryRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    @Mock
    private lateinit var userSessionDataStore: UserSessionDataStore

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var storyRemoteMediator: StoryRemoteMediator

    @Mock
    private lateinit var database: StoryDatabase

    private lateinit var repository: StoryRepository

    @Before
    fun setUp() {
        repository = StoryRepository(
            userSessionDataStore, remoteDataSource, storyRemoteMediator, database
        )
    }

    @Test
    fun `post story then success`() = runTest {
        val file = StoryDataDummy.file
        val desc = StoryDataDummy.validDescription
        val lat = StoryDataDummy.latitude
        val lon = StoryDataDummy.longitude
        val token = StoryDataDummy.token
        val response = StoryDataDummy.postStorySuccess

        Mockito.`when`(userSessionDataStore.getToken()).thenReturn(token)
        Mockito.`when`(remoteDataSource.postNewStory(token, file, desc, lat, lon)).thenReturn(response)

        val result = repository.postNewStory(file, desc, lat, lon)
        assertTrue(result is Resource.Success)
        assertEquals(response.message, result.message)
        assertEquals(response.data, result.data)
    }

    @Test
    fun `post story then failed`() = runTest {
        val file = StoryDataDummy.file
        val desc = StoryDataDummy.validDescription
        val lat = StoryDataDummy.latitude
        val lon = StoryDataDummy.longitude
        val token = StoryDataDummy.token
        val response = StoryDataDummy.postStoryFailed

        Mockito.`when`(userSessionDataStore.getToken()).thenReturn(token)
        Mockito.`when`(remoteDataSource.postNewStory(token, file, desc, lat, lon)).thenReturn(response)

        val result = repository.postNewStory(file, desc, lat, lon)
        assertTrue(result is Resource.Failed)
        assertEquals(response.errorMessage, result.message)
    }

    @Test
    fun `post story then error`() = runTest {
        val file = StoryDataDummy.file
        val desc = StoryDataDummy.validDescription
        val lat = StoryDataDummy.latitude
        val lon = StoryDataDummy.longitude
        val token = StoryDataDummy.token
        val response = StoryDataDummy.postStoryError

        Mockito.`when`(userSessionDataStore.getToken()).thenReturn(token)
        Mockito.`when`(remoteDataSource.postNewStory(token, file, desc, lat, lon)).thenReturn(response)

        val result = repository.postNewStory(file, desc, lat, lon)
        assertTrue(result is Resource.Error)
        assertEquals(response.errorResource, result.messageResource)
    }

    @Test
    fun `get story with location then success`() = runTest {
        val token = StoryDataDummy.token
        val response = StoryDataDummy.getStoryLocationSuccess
        val expected = StoryDataDummy.getStoryLocationMapper

        Mockito.`when`(userSessionDataStore.getToken()).thenReturn(token)
        Mockito.`when`(remoteDataSource.getListStories(token, 1)).thenReturn(response)

        val result = repository.getListStoryWithLocation()
        assertTrue(result is Resource.Success)
        assertEquals(expected, result.data)
    }

    @Test
    fun `get story with location then failed`() = runTest {
        val token = StoryDataDummy.token
        val response = StoryDataDummy.getStoryLocationFailed

        Mockito.`when`(userSessionDataStore.getToken()).thenReturn(token)
        Mockito.`when`(remoteDataSource.getListStories(token, 1)).thenReturn(response)

        val result = repository.getListStoryWithLocation()
        assertTrue(result is Resource.Failed)
        assertEquals(response.errorMessage, result.message)
    }

    @Test
    fun `get story with location then error`() = runTest {
        val token = StoryDataDummy.token
        val response = StoryDataDummy.getStoryLocationError

        Mockito.`when`(userSessionDataStore.getToken()).thenReturn(token)
        Mockito.`when`(remoteDataSource.getListStories(token, 1)).thenReturn(response)

        val result = repository.getListStoryWithLocation()
        assertTrue(result is Resource.Error)
        assertEquals(response.errorResource, result.messageResource)
    }

    @Test
    fun `get value stories then return empty list`() = runTest {
        val dataTest = PagingData.from(emptyList<StoriesEntity>())
        val flowTest = flow {
            emit(dataTest)
        }

        Mockito.`when`(storyRepository.getStoryPaging()).thenReturn(flowTest)
        val result = storyRepository.getStoryPaging()

        Mockito.verify(storyRepository).getStoryPaging()
        assertEquals(flowTest, result)
    }

    @Test
    fun `get value stories then return list`() = runTest {
        val dataTest = PagingData.from(StoryDataDummy.generateDummyStoryResponse(1, 50))
        val flowTest = flow {
            emit(dataTest)
        }

        Mockito.`when`(storyRepository.getStoryPaging()).thenReturn(flowTest)
        val result = storyRepository.getStoryPaging()

        Mockito.verify(storyRepository).getStoryPaging()
        assertEquals(flowTest, result)
    }

}