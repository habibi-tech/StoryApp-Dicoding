package com.habibi.core.domain.widget

import com.habibi.core.data.Resource
import com.habibi.core.domain.repository.IWidgetRepository
import com.habibi.core.domain.widget.data.WidgetItem
import com.habibi.core.domain.widget.usecase.IWidgetUseCase
import javax.inject.Inject

class WidgetInteractor @Inject constructor(
    private val widgetRepository: IWidgetRepository
): IWidgetUseCase {

    override suspend fun getListStory(): Resource<List<WidgetItem>> =
        widgetRepository.getListStory()

}