package br.com.nicolas.core.data.repository

interface CharacterRemoteDataSource<T> {

    suspend fun fetchCharacters(queries: Map<String, String>) : T

}