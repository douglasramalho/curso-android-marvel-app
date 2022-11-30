package br.com.liebersonsantos.core.data.repository

import androidx.paging.PagingSource
import br.com.liebersonsantos.core.domain.model.Character

interface CharactersRepository {
    fun getCharacters(query: String): PagingSource<Int, Character>
}