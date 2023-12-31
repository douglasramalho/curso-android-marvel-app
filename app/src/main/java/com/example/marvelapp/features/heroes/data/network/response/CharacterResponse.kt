package com.example.marvelapp.features.heroes.data.network.response

import com.example.marvelapp.commons.data.network.response.ThumbnailResponse

data class CharacterResponse(
    val id: String,
    val name: String,
    val thumbnail: ThumbnailResponse
)
