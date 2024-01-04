package com.example.marvelapp.features.heroes.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelapp.commons.data.network.response.DataWrapperResponse
import com.example.marvelapp.commons.utils.Constants.LIMIT
import com.example.marvelapp.features.heroes.data.network.datasource.CharactersRemoteDataSource
import com.example.marvelapp.features.heroes.data.response.toCharacterEntity
import com.example.marvelapp.features.heroes.domain.entities.CharacterEntity

class CharactersPagingSource(
    private val remoteDataSource: CharactersRemoteDataSource<DataWrapperResponse>,
    private val query: String
): PagingSource<Int, CharacterEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterEntity> {
        return try {
            val offset = params.key ?: 0
            val queries = hashMapOf(
                "offset" to offset.toString()
            )
            if(query.isNotEmpty()){
                queries["nameStartsWith"] = query
            }

            val response = remoteDataSource.fetchCharacters(queries)
            val responseOffset = response.data.offset
            val totalCharacters = response.data.total

            LoadResult.Page(
                data = response.data.results.map {
                    it.toCharacterEntity()
                },
                prevKey = null,
                nextKey = if(responseOffset < totalCharacters) responseOffset + LIMIT else null
            )
        } catch (exception: Exception){
            LoadResult.Error(exception)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, CharacterEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LIMIT) ?: anchorPage?.nextKey?.minus(LIMIT)
        }
    }
}