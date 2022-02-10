package br.com.nicolas.core.data.repository

import androidx.paging.PagingSource
import br.com.nicolas.core.domain.model.Character

interface CharacterRepository {

    fun getCharacters(query: String): PagingSource<Int, Character>
}