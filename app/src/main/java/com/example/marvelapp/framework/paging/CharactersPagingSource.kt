package com.example.marvelapp.framework.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import com.example.marvelapp.framework.network.response.toCharacterModel
import com.github.coutinhonobre.core.data.repository.CharactersRemoteDataSource
import com.github.coutinhonobre.core.domain.model.Character

class CharactersPagingSource(
    private val remoteDataSource: CharactersRemoteDataSource<DataWrapperResponse>,
    private val query: String
) : PagingSource<Int, Character>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val offset = params.key ?: 0

        val queries = hashMapOf(
            "offset" to offset.toString()
        )

        if (query.isNotEmpty()) {
            queries["nameStartsWith"] = query
        }

        val response = remoteDataSource.fetchCharacters(queries = queries)

        val responseOffset = response.data.offset
        val totalCharacters = response.data.total

        return LoadResult.Page(
            data = response.data.results.map { it.toCharacterModel() },
            prevKey = null,
            nextKey = if (responseOffset < totalCharacters) {
                responseOffset + LIMIT
            } else null
        )
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
