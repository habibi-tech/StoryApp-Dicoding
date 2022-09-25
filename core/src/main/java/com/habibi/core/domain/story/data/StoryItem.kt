package com.habibi.core.domain.story.data

data class StoryItem (
    val id: String = "",
    val photoUrl: String = "",
    val name: String = "",
    val description: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0
)