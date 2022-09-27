package com.example.core.data.repository

import androidx.paging.PagingSource
import com.example.core.domain.model.Character

interface CharactersRepository {

    fun getCharacters(query: String): PagingSource<Int, Character>
}