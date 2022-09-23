package com.habibi.storyapp.features.authentication.presentation.register

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
class RegisterViewModel @Inject constructor(
    private val useCase: IAuthenticationUseCase
): ViewModel() {

    private val _register = MutableLiveData<Resource<Unit>>()
    val register: LiveData<Resource<Unit>> get() = _register

    private val _fieldValid = MutableLiveData(false)
    val fieldValid: LiveData<Boolean> get() = _fieldValid

    fun checkFieldValidation(
        nameError: CharSequence?,
        name: String,
        emailError: CharSequence?,
        email: String,
        passwordError: CharSequence?,
        password: String
    ) {
        _fieldValid.value =
            nameError.isNullOrEmpty() && name.isNotEmpty() &&
            emailError.isNullOrEmpty() && email.isNotEmpty() &&
            passwordError.isNullOrEmpty() && password.isNotEmpty()
    }

    fun postRegister(name: String, email: String, password: String) {
        _register.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            _register.postValue(useCase.postRegister(name, email, password))
        }
    }

}