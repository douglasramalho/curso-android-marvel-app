package com.example.marvelapp.framework.network.response

data class CharacterResponse(
    val id: String,
    val nome: String,
    val thumbnail: ThumbnailResponse,
)
