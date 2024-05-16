package com.example.marvelapp.framework.network.response

import com.example.core.domain.model.Character

data class DataContainerResponse(
    val offset: Int,
    val total: Int,
    val results: List<CharacterResponse>
)