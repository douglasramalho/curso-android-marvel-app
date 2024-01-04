package com.example.marvelapp.features.heroes.data.network.repository

import androidx.paging.PagingSource
import com.example.marvelapp.commons.data.network.response.DataWrapperResponse
import com.example.marvelapp.features.heroes.data.network.datasource.CharactersRemoteDataSource
import com.example.marvelapp.features.heroes.data.paging.CharactersPagingSource
import com.example.marvelapp.features.heroes.domain.entities.CharacterEntity
import javax.inject.Inject

interface CharactersRepository {
    fun getCharacters(query: String): PagingSource<Int, CharacterEntity>
}

class CharactersRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharactersRemoteDataSource<DataWrapperResponse>
): CharactersRepository {
    override fun getCharacters(query: String): PagingSource<Int, CharacterEntity> {
        return CharactersPagingSource(remoteDataSource, query)
    }
}