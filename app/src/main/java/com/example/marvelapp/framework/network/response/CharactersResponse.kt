package com.example.marvelapp.framework.network.response

data class CharactersResponse(
    val id: String,
    val name: String,
    val thumbnail: ThumbnailResponse
)
