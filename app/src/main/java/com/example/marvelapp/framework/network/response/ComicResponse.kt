package com.example.marvelapp.framework.network.response

import com.example.core.domain.model.Comic
import com.google.gson.annotations.SerializedName

class ComicResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailResponse
)

fun ComicResponse.toComicModel(): Comic {
    return Comic(
        id = this.id,
        imageUrl = "${this.thumbnail.path}.${this.thumbnail.extension}"
            .replace("http", "https")
    )
}