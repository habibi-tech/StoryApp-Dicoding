package com.habibi.storyapp.features.story.presentation.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habibi.core.data.Resource
import com.habibi.core.domain.story.usecase.IStoryAddUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class StoryAddViewModel @Inject constructor(
    private val useCase: IStoryAddUseCase
): ViewModel() {

    private val _newStory = MutableLiveData<Resource<Unit>>()
    val newStory: LiveData<Resource<Unit>> get() = _newStory

    private val _fieldValid = MutableLiveData(false)
    val fieldValid: LiveData<Boolean> get() = _fieldValid

    private var _photoPath = ""

    fun checkFieldValidation(descriptionError: CharSequence?, description: String) {
        _fieldValid.value = descriptionError.isNullOrEmpty() && description.isNotEmpty() &&_photoPath.isNotEmpty()
    }

    fun postNewStory(description: String) {
        _newStory.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            _newStory.postValue(useCase.postNewStory(_photoPath, description))
        }
    }

}