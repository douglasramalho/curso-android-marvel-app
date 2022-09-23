package com.example.marvelapp.framework.network.response

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailResponse
)
