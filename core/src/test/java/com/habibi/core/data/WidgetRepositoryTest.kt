package com.habibi.core.data

import com.habibi.core.data.source.preference.UserSessionDataStore
import com.habibi.core.data.source.remote.RemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class WidgetRepositoryTest {

    @Mock
    private lateinit var userSessionDataStore: UserSessionDataStore

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    private lateinit var repository: WidgetRepository

    @Before
    fun setUp() {
        repository = WidgetRepository(
            userSessionDataStore, remoteDataSource
        )
    }

    @Test
    fun `get story widget then error`() = runTest {
        val token = WidgetDataDummy.token
        val response = WidgetDataDummy.getStoryWidgetError

        Mockito.`when`(userSessionDataStore.getToken()).thenReturn(token)
        Mockito.`when`(remoteDataSource.getListStories(token)).thenReturn(response)

        val result = repository.getListStory()
        assertTrue(result is Resource.Error)
        assertEquals(response.errorResource, result.messageResource)
    }

    @Test
    fun `get story widget then failed`() = runTest {
        val token = WidgetDataDummy.token
        val response = WidgetDataDummy.getStoryWidgetFailed

        Mockito.`when`(userSessionDataStore.getToken()).thenReturn(token)
        Mockito.`when`(remoteDataSource.getListStories(token)).thenReturn(response)

        val result = repository.getListStory()
        assertTrue(result is Resource.Failed)
        assertEquals(response.errorMessage, result.message)
    }

    @Test
    fun `get story with location then success`() = runTest {
        val token = WidgetDataDummy.token
        val response = WidgetDataDummy.getStoryWidgetSuccess
        val expected = WidgetDataDummy.getStoryWidgetMapper

        Mockito.`when`(userSessionDataStore.getToken()).thenReturn(token)
        Mockito.`when`(remoteDataSource.getListStories(token)).thenReturn(response)

        val result = repository.getListStory()
        assertTrue(result is Resource.Success)
        assertEquals(expected, result.data)
        assertEquals(response.message, result.message)
    }
    
}