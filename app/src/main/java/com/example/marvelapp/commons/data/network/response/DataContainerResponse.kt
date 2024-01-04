package com.example.marvelapp.commons.data.network.response

import com.example.marvelapp.features.heroes.data.response.CharacterResponse

data class DataContainerResponse(
    val offset: Int,
    val total: Int,
    val results: List<CharacterResponse>
)
