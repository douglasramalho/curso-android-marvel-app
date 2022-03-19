package com.example.marvelapp.framework.network.response

import com.example.core.domain.model.Character

data class CharacterResponse(
    val id: String,
    val nome: String,
    val thumbnail: ThumbnailResponse,
)

fun CharacterResponse.toCharacterModel(): Character{
    return Character(
        name = this.nome,
        imageUrl = "${this.thumbnail.path}.${this.thumbnail.extension}"
    )
}