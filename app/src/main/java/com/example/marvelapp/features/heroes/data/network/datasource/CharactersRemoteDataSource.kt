package com.example.marvelapp.features.heroes.data.network.datasource

import com.example.marvelapp.commons.data.network.response.DataWrapperResponse
import com.example.marvelapp.framework.service.MarvelApi
import javax.inject.Inject

interface CharactersRemoteDataSource<T> {
    suspend fun fetchCharacters(queries: Map<String, String>): T
}

class CharactersRemoteDataSourceImpl @Inject constructor(
    private val marvelApi: MarvelApi
): CharactersRemoteDataSource<DataWrapperResponse> {
    override suspend fun fetchCharacters(queries: Map<String, String>): DataWrapperResponse {
        return marvelApi.getCharacters(queries)
    }
}