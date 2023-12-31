package com.example.marvelapp.commons.data.network.response

import com.example.marvelapp.features.heroes.data.network.response.CharacterResponse

data class DataContainerResponse(
    val results: List<CharacterResponse>
)
