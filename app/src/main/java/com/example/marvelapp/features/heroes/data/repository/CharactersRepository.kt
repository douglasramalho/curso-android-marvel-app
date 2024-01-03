package com.example.marvelapp.features.heroes.data.repository

import androidx.paging.PagingSource
import com.example.marvelapp.features.heroes.domain.model.Character

interface CharactersRepository {
    fun getCharacters(query: String): PagingSource<Int, Character>
}