package com.habibi.storyapp.features.story.presentation.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habibi.core.data.Resource
import com.habibi.core.domain.story.data.StoryItem
import com.habibi.core.domain.story.usecase.IStoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class StoryMapsViewModel @Inject constructor(
    private val useCase: IStoryUseCase
): ViewModel() {

    private val _listStoryWithLocation = MutableLiveData<Resource<List<StoryItem>>>()
    val listStoryWithLocation: LiveData<Resource<List<StoryItem>>> get() = _listStoryWithLocation

    init {
        getListStoryWithLocation()
    }

    fun getListStoryWithLocation() = viewModelScope.launch(Dispatchers.IO) {
        _listStoryWithLocation.postValue(Resource.Loading())
        _listStoryWithLocation.postValue(useCase.getListStoryWithLocation())
    }

}