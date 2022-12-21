package com.example.marvelapp.framework.network.response

import com.example.core.domain.model.Character
import com.example.core.domain.model.Comic
import com.google.gson.annotations.SerializedName

data class ComicResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailResponse
)

fun ComicResponse.toComicModel(): Comic {
    return Comic (
        id = this.id,
        imageUrl = this.thumbnail.getHttpsUrl()
    )
}