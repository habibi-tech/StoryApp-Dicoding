package com.habibi.storyapp.features.splashscreen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habibi.core.domain.splashscreen.usecase.ISplashScreenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val useCase: ISplashScreenUseCase
): ViewModel() {

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> get() = _isLoggedIn

    init {
        isLoggedIn()
    }

    private fun isLoggedIn() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoggedIn.postValue(useCase.isLoggedIn())
        }
    }

}