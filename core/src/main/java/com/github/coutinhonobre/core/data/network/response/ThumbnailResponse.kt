package com.github.coutinhonobre.core.data.network.response

import com.google.gson.annotations.SerializedName

data class ThumbnailResponse(
    val path: String,
    @SerializedName("extension")
    val extensionPath: String
)
