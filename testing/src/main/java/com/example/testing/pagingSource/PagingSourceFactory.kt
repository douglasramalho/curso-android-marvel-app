package com.example.testing.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.coutinhonobre.core.domain.model.Character

private const val LIMIT = 20

object PagingSourceFactory {
    fun create(heroes: List<Character>) = object : PagingSource<Int, Character>() {
        override fun getRefreshKey(state: PagingState<Int, Character>): Int = 1

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> =
            LoadResult.Page(
                data = heroes,
                prevKey = null,
                nextKey = LIMIT
            )

    }
}
