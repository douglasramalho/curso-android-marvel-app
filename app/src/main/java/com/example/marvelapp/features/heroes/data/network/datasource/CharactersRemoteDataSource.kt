package com.example.marvelapp.features.heroes.data.network.datasource

import com.example.marvelapp.commons.data.network.response.DataWrapperResponse

interface CharactersRemoteDataSource<T> {
    suspend fun fetchCharacters(queries: Map<String, String>): T
}