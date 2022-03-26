package com.example.testing.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.domain.model.Character

class PagingSourceFactory {
    @Suppress("MagicNumber")
    fun create(heroes: List<Character>) = object : PagingSource<Int, Character>() {
        override fun getRefreshKey(state: PagingState<Int, Character>): Int = 1
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
            return LoadResult.Page(
                heroes,
                null,
                20
            )
        }
    }
}