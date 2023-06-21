package com.example.marvelapp.framework.network.response

import com.google.gson.annotations.SerializedName

data class ThumbnailResponse(
    @SerializedName("path")
    val path: String,
    @SerializedName("extension")
    val extension: String
)

val ThumbnailResponse.imageUrl: String
    get() = "${path}.${extension}".replace("http", "https")