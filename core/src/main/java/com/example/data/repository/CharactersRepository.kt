package com.example.data.repository

import androidx.paging.PagingSource
import com.example.domain.model.Character

interface CharactersRepository {

    fun getCharacters(query: String): PagingSource<Int, Character>

}