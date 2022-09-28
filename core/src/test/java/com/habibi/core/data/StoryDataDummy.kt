package com.habibi.core.data

import com.habibi.core.data.source.local.entity.StoriesEntity
import com.habibi.core.data.source.remote.network.ApiResponse
import com.habibi.core.data.source.remote.response.ListStoryItem
import com.habibi.core.domain.story.data.StoryItem
import java.io.File

object StoryDataDummy {

    const val latitude = 1.00F
    const val longitude = 2.00F
    val file: File = File.createTempFile("temporary", "dummy")
    const val validDescription = "Tes"
    private const val message = "message"
    private const val messageResource = 1
    const val token = "token"

    val postStorySuccess = ApiResponse.Success(Unit, message)
    val postStoryFailed = ApiResponse.Failed(message)
    val postStoryError = ApiResponse.Error(messageResource)

    val getStoryLocationSuccess = ApiResponse.Success(generateDummyStoryWithLocation(), message)
    val getStoryLocationFailed = ApiResponse.Failed(message)
    val getStoryLocationError = ApiResponse.Error(messageResource)
    val getStoryLocationMapper = generateDummyStoryWithLocation().map {
        StoryItem(
            id = it.id,
            photoUrl = it.photoUrl,
            name = it.name,
            description = it.description,
            lat = it.lat,
            lon = it.lon
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

    fun generateDummyStoryResponse(page: Int, size: Int): List<StoriesEntity> {
        val items: MutableList<StoriesEntity> = arrayListOf()
        for (i in 0..100) {
            val story = StoriesEntity(
                i.toString(),
                "photo + $i",
                "name $i",
                "desc $i"
            )
            items.add(story)
        }
        return items.subList((page - 1) * size, (page - 1) * size + size)
    }

}