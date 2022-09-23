package com.habibi.storyapp.features.authentication.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habibi.core.data.Resource
import com.habibi.core.domain.authentication.usecase.IAuthenticationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: IAuthenticationUseCase
): ViewModel() {

    private val _login = MutableLiveData<Resource<Unit>>()
    val login: LiveData<Resource<Unit>> get() = _login

    private val _fieldValid = MutableLiveData(false)
    val fieldValid: LiveData<Boolean> get() = _fieldValid

    fun checkFieldValidation(emailError: CharSequence?, email: String, passwordError: CharSequence?, password: String) {
        _fieldValid.value = emailError.isNullOrEmpty() && email.isNotEmpty() && passwordError.isNullOrEmpty() && password.isNotEmpty()
    }

    fun postLogin(email: String, password: String) {
        _login.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            _login.postValue(useCase.postLogin(email, password))
        }
    }

}