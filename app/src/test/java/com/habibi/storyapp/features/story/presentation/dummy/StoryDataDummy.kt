package com.habibi.storyapp.features.story.presentation.dummy

import com.habibi.core.data.source.local.entity.StoriesEntity
import com.habibi.core.domain.story.data.StoryItem
import java.io.File

object StoryDataDummy {

    const val name = "Habibi"
    const val photoPath = "Local/c"
    const val latitude = 1.00F
    const val longitude = 2.00F
    val file: File = File.createTempFile("temporary", "dummy")
    const val validDescription = "Tes"
    const val validDescriptionError = ""
    const val invalidDescription = ""
    const val invalidDescriptionError = "Required!"
    const val message = "message"
    const val messageResource = 1

    val listStory = listOf(StoryItem(), StoryItem(), StoryItem())

    fun generateDummyStoryResponse(): List<StoriesEntity> {
        val items: MutableList<StoriesEntity> = arrayListOf()
        for (i in 0..100) {
            val story = StoriesEntity(
                i.toString(),
                "author + $i",
                "quote $i",
                "desc $i"
            )
            items.add(story)
        }
        return items
    }

    fun generateDummyEmptyStoryResponse() = emptyList<StoriesEntity>()
}