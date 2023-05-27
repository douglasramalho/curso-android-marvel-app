package com.example.marvelapp.framework.network.response

import com.example.domain.model.Character

data class CharacterResponse(
    val id: String,
    val name: String,
    val thumbnail: ThumbnailResponse
)

fun CharacterResponse.toCharacterModel(): Character {
    return Character(
        name = name,
        imageUrl = thumbnail.imageUrl

    )
}
