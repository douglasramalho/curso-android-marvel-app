package com.example.marvelapp.framework.network.response

import com.github.coutinhonobre.core.domain.model.Character
import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailResponse
)

fun CharacterResponse.toCharacterModel() = Character(
    name = this.name,
    imageUrl = "${this.thumbnail.path}.${this.thumbnail.extensionPath}"
        .replace("http", "https")
)
