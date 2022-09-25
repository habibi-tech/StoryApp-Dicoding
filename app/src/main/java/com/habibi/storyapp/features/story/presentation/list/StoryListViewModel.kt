package com.habibi.storyapp.features.story.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.habibi.core.data.source.local.entity.StoriesEntity
import com.habibi.core.domain.story.usecase.IStoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StoryListViewModel @Inject constructor(
    useCase: IStoryUseCase
): ViewModel() {

    val getListPaging: LiveData<PagingData<StoriesEntity>> =
        useCase.getStoryPaging().cachedIn(viewModelScope).asLiveData()

}