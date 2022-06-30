package com.example.marvelapp.framework.network.response

import br.com.dio.core.domain.model.Character
import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailResponse
)

fun CharacterResponse.toCharacterModel(): Character {
    return Character(
        // converter de characterResponse para o modelo do nosso dominio
        name = this.name,
        // sabemos que esse thumbnail.path traz a url com http
        // dps que eu construir meu path com a extensao
        imgUrl = "${this.thumbnail.path}.${this.thumbnail.extension}"
            .replace("http", "https")
    )
}

