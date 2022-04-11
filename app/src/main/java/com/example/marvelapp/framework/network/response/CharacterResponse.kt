package com.example.marvelapp.framework.network.response
import br.com.dio.core.domain.model.Character

data class CharacterResponse(
    val id: String,
    val name: String,
    val thumbnail: ThumbnailResponse
)

fun CharacterResponse.toCharacterModel() : Character{
    return Character(
        // converter de characterResponse para o modelo do nosso dominio
        name = this.name,
        imgUrl = "${this.thumbnail.path}.${this.thumbnail.extension}"
    )
}

