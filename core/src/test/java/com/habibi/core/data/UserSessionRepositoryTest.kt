package com.habibi.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.habibi.core.data.source.preference.UserSessionDataStore
import com.habibi.core.data.source.remote.RemoteDataSource
import com.habibi.core.utils.MainDispatcherRule
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
class UserSessionRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var userSessionDataStore: UserSessionDataStore

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var repository: UserSessionRepository

    @Before
    fun setUp() {
        repository = UserSessionRepository(
            userSessionDataStore, remoteDataSource
        )
    }

    @Test
    fun `set user logout then required method called once`() = runTest{
        repository.setUserLogout()

        Mockito.verify(userSessionDataStore).removeUserName()
        Mockito.verify(userSessionDataStore).removeToken()
        Mockito.verify(userSessionDataStore).setLogout()
    }

    @Test
    fun `get username then return name`() = runTest {
        val expected = "Habibi"

        Mockito.`when`(userSessionDataStore.getUserName()).thenReturn(expected)

        val result = repository.getUserName()
        assertEquals(expected, result)
    }

    @Test
    fun `get user logged in then return true`() = runTest{
        val expected = true

        Mockito.`when`(userSessionDataStore.isLoggedIn()).thenReturn(expected)

        val result = repository.isLoggedIn()
        assertTrue(result)
    }

    @Test
    fun `get user logged in then return false`() = runTest{
        val expected = false

        Mockito.`when`(userSessionDataStore.isLoggedIn()).thenReturn(expected)

        val result = repository.isLoggedIn()
        assertFalse(result)
    }

    @Test
    fun `post register then success`() = runTest {
        val name = UserSessionDataDummy.name
        val email = UserSessionDataDummy.email
        val password = UserSessionDataDummy.password
        val response = UserSessionDataDummy.postRegisterSuccess

        Mockito.`when`(remoteDataSource.postRegister(name, email, password)).thenReturn(response)

        val result = repository.postRegister(name, email, password)
        assertTrue(result is Resource.Success)
        assertEquals(response.data, result.data)
        assertEquals(response.message, result.message)
    }

    @Test
    fun `post register then failed`() = runTest {
        val name = UserSessionDataDummy.name
        val email = UserSessionDataDummy.email
        val password = UserSessionDataDummy.password
        val response = UserSessionDataDummy.postRegisterFailed

        Mockito.`when`(remoteDataSource.postRegister(name, email, password)).thenReturn(response)

        val result = repository.postRegister(name, email, password)
        assertTrue(result is Resource.Failed)
        assertEquals(response.errorMessage, result.message)
    }

    @Test
    fun `post register then error`() = runTest {
        val name = UserSessionDataDummy.name
        val email = UserSessionDataDummy.email
        val password = UserSessionDataDummy.password
        val response = UserSessionDataDummy.postRegisterError

        Mockito.`when`(remoteDataSource.postRegister(name, email, password)).thenReturn(response)

        val result = repository.postRegister(name, email, password)
        assertTrue(result is Resource.Error)
        assertEquals(response.errorResource, result.messageResource)
    }

    @Test
    fun `post login then success`() = runTest {
        val email = UserSessionDataDummy.email
        val password = UserSessionDataDummy.password
        val response = UserSessionDataDummy.postLoginSuccess

        Mockito.`when`(remoteDataSource.postLogin(email, password)).thenReturn(response)

        val result = repository.postLogin(email, password)
        assertTrue(result is Resource.Success)
        assertEquals(Unit, result.data)
        assertEquals(response.message, result.message)
    }

    @Test
    fun `post login then failed`() = runTest {
        val email = UserSessionDataDummy.email
        val password = UserSessionDataDummy.password
        val response = UserSessionDataDummy.postLoginFailed

        Mockito.`when`(remoteDataSource.postLogin(email, password)).thenReturn(response)

        val result = repository.postLogin(email, password)
        assertTrue(result is Resource.Failed)
        assertEquals(response.errorMessage, result.message)
    }

    @Test
    fun `post login then error`() = runTest {
        val email = UserSessionDataDummy.email
        val password = UserSessionDataDummy.password
        val response = UserSessionDataDummy.postLoginError

        Mockito.`when`(remoteDataSource.postLogin(email, password)).thenReturn(response)

        val result = repository.postLogin(email, password)
        assertTrue(result is Resource.Error)
        assertEquals(response.errorResource, result.messageResource)
    }

}