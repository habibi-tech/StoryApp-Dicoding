package com.habibi.core.domain.widget.usecase

import com.habibi.core.data.Resource
import com.habibi.core.domain.widget.data.WidgetItem

interface IWidgetUseCase {

    suspend fun getListStory(): Resource<List<WidgetItem>>

}