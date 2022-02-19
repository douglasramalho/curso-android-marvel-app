package com.example.core.data.network.response

import com.example.core.data.network.response.ThumbnailResponse

data class CharacterResponse(
    val id: String,
    val name: String,
    val thumbnail: ThumbnailResponse
)
