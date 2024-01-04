package com.example.marvelapp.features.heroes.data.response

import com.example.marvelapp.commons.data.network.response.ThumbnailResponse
import com.example.marvelapp.features.heroes.domain.entities.CharacterEntity

data class CharacterResponse(
    val id: String,
    val name: String,
    val thumbnail: ThumbnailResponse
)

fun CharacterResponse.toCharacterEntity(): CharacterEntity {
    return CharacterEntity(
        name = this.name,
        imageUrl = "${this.thumbnail.path}.${this.thumbnail.extension}"
            .replace("http", "https")
    )
}
