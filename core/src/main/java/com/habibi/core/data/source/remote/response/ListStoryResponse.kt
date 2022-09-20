package com.habibi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListStoryResponse(

	@SerializedName("listStory")
	val listStory: List<ListStoryItem> = emptyList(),

	@SerializedName("error")
	val error: Boolean = true,

	@SerializedName("message")
	val message: String = ""

)

data class ListStoryItem(

	@SerializedName("photoUrl")
	val photoUrl: String = "",

	@SerializedName("createdAt")
	val createdAt: String = "",

	@SerializedName("name")
	val name: String = "",

	@SerializedName("description")
	val description: String = "",

	@SerializedName("lon")
	val lon: Double = 0.0,

	@SerializedName("id")
	val id: String = "",

	@SerializedName("lat")
	val lat: Double = 0.0

)
