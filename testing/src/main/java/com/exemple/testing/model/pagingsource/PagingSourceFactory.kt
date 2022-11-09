package com.exemple.testing.model.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.core.domain.model.Character

class PagingSourceFactory {
    fun create(heroes: List<Character>) = object : PagingSource<Int, Character>() {
        override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
            TODO("Not yet implemented")
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
            return LoadResult.Page(
                data = heroes,
                prevKey = null,
                nextKey = 20
            )
        }
    }
}