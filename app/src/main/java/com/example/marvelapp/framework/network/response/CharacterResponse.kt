package com.example.marvelapp.framework.network.response

data class CharacterResponse(
    val id: String,
    val name: String,
    val thumbnailResponse: ThumbnailResponse
)
