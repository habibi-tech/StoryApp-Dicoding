package com.habibi.storyapp.features.story.presentation.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habibi.core.data.Resource
import com.habibi.core.domain.story.usecase.IStoryAddUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
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

    private var _photoFile = MutableLiveData<File>()
    val photoFile: LiveData<File> get() = _photoFile

    private var _currentPhotoPath: String = ""
    val currentPhotoPath get() = _currentPhotoPath

    fun setCurrentPhotoPath(path: String) {
        _currentPhotoPath = path
    }

    fun checkFieldValidation(file: File?, descriptionError: CharSequence?, description: String) {
        file?.let { _photoFile.value = it }
        _fieldValid.value = descriptionError.isNullOrEmpty() && description.isNotEmpty() && _photoFile.value != null
    }

    fun postNewStory(description: String) {
        _newStory.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            _newStory.postValue(useCase.postNewStory(photoFile.value!!, description))
        }
    }

}