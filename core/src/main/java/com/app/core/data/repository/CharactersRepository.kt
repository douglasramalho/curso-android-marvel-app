package com.app.core.data.repository

import androidx.paging.PagingSource
import com.app.core.domain.model.Character

interface CharactersRepository {
    fun getCharacters(query: String): PagingSource<Int, Character>
}