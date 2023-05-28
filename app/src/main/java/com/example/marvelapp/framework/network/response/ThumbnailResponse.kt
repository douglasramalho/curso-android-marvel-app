package com.example.marvelapp.framework.network.response

import com.example.domain.model.Character

data class ThumbnailResponse(
    val path: String,
    val extension: String
)

val ThumbnailResponse.imageUrl: String
    get() = "${path}.${extension}".replace("http", "https")