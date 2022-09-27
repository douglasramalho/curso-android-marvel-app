package com.example.marvelapp.framework.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.data.repository.CharactersRemoteDataSource
import com.example.core.domain.model.Character
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import com.example.marvelapp.framework.network.response.toCharacterModel

class CharactersPagingSource(
    private val remoteDataSource: CharactersRemoteDataSource<DataWrapperResponse>,
    private val query: String
) : PagingSource<Int, Character>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val offset = params.key ?: KEY_OFFSET

            val quries = hashMapOf(
                "offset" to offset.toString()
            )

            if (query.isNotEmpty()) {
                quries["nameStartsWith"] = query
            }

            val response = remoteDataSource.fetchCharacters(quries)
            val responseOffset = response.data.offset
            val totalCharacters = response.data.total

            LoadResult.Page(
                data = response.data.results.map { it.toCharacterModel() },
                prevKey = null,
                nextKey = if (responseOffset < totalCharacters) responseOffset + LIMIT else null
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
        private const val KEY_OFFSET = 0
    }
}