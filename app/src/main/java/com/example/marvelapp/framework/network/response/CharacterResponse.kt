package com.example.marvelapp.framework.network.response

import com.github.coutinhonobre.core.domain.model.Character

data class CharacterResponse(
    val id: String,
    val name: String,
    val thumbnail: ThumbnailResponse
)

fun CharacterResponse.toCharacterModel() = Character(
    name = this.name,
    imageUrl = "${this.thumbnail.path}.${this.thumbnail.extensionPath}"
)
