package com.example.marvelapp.framework.network.response

import com.example.core.domain.model.Character
import com.google.gson.annotations.SerializedName

data class DataContainerResponse(
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("results")
    val results: List<CharacterResponse>
)