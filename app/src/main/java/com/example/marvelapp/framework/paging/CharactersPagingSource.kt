package com.example.marvelapp.framework.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.data.repository.CharactersRemoteDataSource
import com.example.core.domain.model.Character
/**
 * PagingSource vai buscar unicamente uma fonte de dados remota,
 * mas se for necessário buscar uma fonte de dados remota e local como
 * o Room, então é necessário usar o MediatorSource
 * **/
class CharactersPagingSource(
    private val remoteDataSource: CharactersRemoteDataSource,
    private val query: String
) : PagingSource<Int, Character>() {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            // na primeira vez ele vai estar null e se tiver null vai começar com 0
            val offset = params.key ?: 0

            val queries = hashMapOf(
                "offset" to offset.toString()
            )
            if (query.isNotEmpty()) {
                queries["nameStartsWith"] = query
            }
            val characterPaging = remoteDataSource.fetchCharacter(queries)
            val responseOffset = characterPaging.offset
            val totalCharacters = characterPaging.total
            LoadResult.Page(
                data = characterPaging.character,
                prevKey = null,
                nextKey = if (responseOffset < totalCharacters) {
                    responseOffset + LIMIT
                } else null
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LIMIT) ?: anchorPage?.nextKey?.minus(LIMIT)
        }
    }

    companion object {
        private const val LIMIT = 20
    }
}