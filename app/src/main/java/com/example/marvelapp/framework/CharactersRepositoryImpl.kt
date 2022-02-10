package com.example.marvelapp.framework

import androidx.paging.PagingSource
import br.com.nicolas.core.data.repository.CharacterRemoteDataSource
import br.com.nicolas.core.data.repository.CharacterRepository
import br.com.nicolas.core.domain.model.Character
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharacterRemoteDataSource<DataWrapperResponse>
) : CharacterRepository {

    override fun getCharacters(query: String): PagingSource<Int, Character> {
        return CharactersPaging()
    }
}