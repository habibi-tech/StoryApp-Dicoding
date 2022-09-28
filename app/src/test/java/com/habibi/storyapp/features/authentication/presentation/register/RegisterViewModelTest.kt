package com.habibi.storyapp.features.authentication.presentation.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.habibi.core.data.Resource
import com.habibi.core.domain.authentication.usecase.IAuthenticationUseCase
import com.habibi.storyapp.features.authentication.presentation.dummy.AuthenticationDataDummy
import com.habibi.storyapp.features.utils.MainDispatcherRule
import com.habibi.storyapp.features.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var useCase : IAuthenticationUseCase

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {
        viewModel = RegisterViewModel(useCase)
    }

    @Test
    fun `input invalid value then field not valid`() {
        val invalidEmail = AuthenticationDataDummy.invalidEmail
        val invalidEmailError = AuthenticationDataDummy.invalidEmailError
        val invalidPassword = AuthenticationDataDummy.invalidPassword
        val invalidPasswordError = AuthenticationDataDummy.invalidPasswordError
        val invalidName = AuthenticationDataDummy.invalidName
        val invalidNameError = AuthenticationDataDummy.invalidNameError
        val expected = false

        viewModel.checkFieldValidation(
            emailError = invalidEmailError,
            email = invalidEmail,
            passwordError = invalidPasswordError,
            password = invalidPassword,
            nameError = invalidNameError,
            name = invalidName
        )

        val result = viewModel.fieldValid.getOrAwaitValue()
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `input valid value then field valid`() {
        val validEmail = AuthenticationDataDummy.validEmail
        val validEmailError = AuthenticationDataDummy.validEmailError
        val validPassword = AuthenticationDataDummy.validPassword
        val validPasswordError = AuthenticationDataDummy.validPasswordError
        val validName = AuthenticationDataDummy.validName
        val validNameError = AuthenticationDataDummy.validNameError
        val expected = true

        viewModel.checkFieldValidation(
            emailError = validEmailError,
            email = validEmail,
            passwordError = validPasswordError,
            password = validPassword,
            nameError = validNameError,
            name = validName
        )

        val result = viewModel.fieldValid.getOrAwaitValue()
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `post register then success`() = runTest {
        val email = AuthenticationDataDummy.validEmail
        val password = AuthenticationDataDummy.validPassword
        val name = AuthenticationDataDummy.validName
        val message = AuthenticationDataDummy.message
        val expected = Resource.Success(Unit, message)

        Mockito.`when`(useCase.postRegister(name, email, password)).thenReturn(expected)
        viewModel.postRegister(name, email, password).join()

        val result = viewModel.register.getOrAwaitValue()
        Assert.assertTrue(result is  Resource.Success)
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `post login then failed`() = runTest {
        val email = AuthenticationDataDummy.validEmail
        val password = AuthenticationDataDummy.validPassword
        val name = AuthenticationDataDummy.validName
        val message = AuthenticationDataDummy.message
        val expected = Resource.Failed<Unit>(message)

        Mockito.`when`(useCase.postRegister(name, email, password)).thenReturn(expected)
        viewModel.postRegister(name, email, password).join()

        val login = viewModel.register.getOrAwaitValue()
        Assert.assertTrue(login is Resource.Failed)
        Assert.assertEquals(expected, login)
    }

    @Test
    fun `post login then error`() = runTest {
        val email = AuthenticationDataDummy.validEmail
        val password = AuthenticationDataDummy.validPassword
        val name = AuthenticationDataDummy.validName
        val message = AuthenticationDataDummy.messageResource
        val expected = Resource.Error<Unit>(message)

        Mockito.`when`(useCase.postRegister(name, email, password)).thenReturn(expected)
        viewModel.postRegister(name, email, password).join()

        val login = viewModel.register.getOrAwaitValue()
        Assert.assertTrue(login is Resource.Error)
        Assert.assertEquals(expected, login)
    }

}