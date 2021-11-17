package com.example.marvelapp.framework.network.response

import com.google.gson.annotations.SerializedName

data class DataWrapperResponse(
    @SerializedName("copyright")
    var copyright: String,
    @SerializedName("data")
    var data: DataContainerResponse
)
