package com.habibi.storyapp.features.story.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habibi.core.data.Resource
import com.habibi.core.domain.story.data.StoryItem
import com.habibi.core.domain.story.usecase.IStoryListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class StoryListViewModel @Inject constructor(
    private val useCase: IStoryListUseCase
): ViewModel() {

    private val _listStory = MutableLiveData<Resource<List<StoryItem>>>()
    val listStory: LiveData<Resource<List<StoryItem>>> get() = _listStory

    init {
        getListStory()
    }

    fun getListStory() {
        _listStory.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            _listStory.postValue(useCase.getListStory())
        }
    }
}