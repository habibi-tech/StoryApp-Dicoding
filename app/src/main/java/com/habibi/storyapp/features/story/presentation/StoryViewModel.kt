package com.habibi.storyapp.features.story.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habibi.core.domain.story.usecase.IStoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val useCase: IStoryUseCase
): ViewModel() {

    private var _userName = ""
    val userName get() = _userName

    fun setUserNotLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.setUserLogout()
        }
    }

    fun getUserName() {
        viewModelScope.launch(Dispatchers.IO) {
            _userName = useCase.getUserName()
        }
    }

}