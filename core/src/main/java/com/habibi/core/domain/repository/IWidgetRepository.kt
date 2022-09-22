package com.habibi.core.domain.repository

import com.habibi.core.data.Resource
import com.habibi.core.domain.widget.data.WidgetItem

interface IWidgetRepository {

    suspend fun getListStory(): Resource<List<WidgetItem>>

}