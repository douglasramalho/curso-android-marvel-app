package com.github.coutinhonobre.core.data.repository

import androidx.paging.PagingSource
import com.github.coutinhonobre.core.domain.model.Character

interface CharactersRepository {
    fun getCharacters(query: String): PagingSource<Int, Character>
}
