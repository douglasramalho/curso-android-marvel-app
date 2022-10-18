package com.example.marvelapp.framework.network.response

import com.google.gson.annotations.SerializedName

data class ThumbnailResponse(
    val path: String,
    @SerializedName("extension")
    val extension: String
)
