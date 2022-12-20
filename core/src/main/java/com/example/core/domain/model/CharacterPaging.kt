package com.example.core.domain.model

import com.example.core.domain.model.Character

data class CharacterPaging (
    val offset: Int,
    val total: Int,
    val character: List<Character>
)
