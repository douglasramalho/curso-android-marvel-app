package com.example.marvelapp.framework

import androidx.paging.PagingSource
import com.app.core.data.repository.CharactersRemoteDataSource
import com.app.core.data.repository.CharactersRepository
import com.app.core.domain.model.Character
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import com.example.marvelapp.framework.paging.CharacterPagingSource
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharactersRemoteDataSource<DataWrapperResponse>
) : CharactersRepository {
    override fun getCharacters(query: String): PagingSource<Int, Character> {
        return CharacterPagingSource(remoteDataSource, query)
    }
}