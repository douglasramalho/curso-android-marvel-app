package com.example.marvelapp.framework.network.response

import com.google.gson.annotations.SerializedName

data class DataContainerResponse(
    @SerializedName("results")
    val results: List<CharacterResponse>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("offset")
    val offset: Int
)
