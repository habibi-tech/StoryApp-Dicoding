package com.habibi.core.data

import com.habibi.core.data.source.remote.network.ApiResponse
import com.habibi.core.data.source.remote.response.ListStoryItem
import com.habibi.core.domain.widget.data.WidgetItem

object WidgetDataDummy {

    private const val messageResource = 1
    private const val message = "message"
    const val token = "token"

    val getStoryWidgetSuccess = ApiResponse.Success(
        generateDummyStoryWithLocation(),
        message
    )
    val getStoryWidgetFailed = ApiResponse.Failed(message)
    val getStoryWidgetError = ApiResponse.Error(messageResource)
    val getStoryWidgetMapper = generateDummyStoryWithLocation().map {
        WidgetItem(
            photoUrl = it.photoUrl,
            name = it.name
        )
    }

    private fun generateDummyStoryWithLocation(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..10) {
            val story = ListStoryItem(
                id = i.toString(),
                name = "author + $i",
                description = "desc $i",
                lat = "$i".toDouble(),
                lon = "$i".toDouble(),
                photoUrl = "url $i",
                createdAt = "time $i"
            )
            items.add(story)
        }
        return items
    }
    
}