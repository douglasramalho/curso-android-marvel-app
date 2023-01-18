package com.example.marvelapp.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.coutinhonobre.core.domain.model.Character
import com.github.coutinhonobre.core.usecase.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    fun charactersPagingData(query: String): Flow<PagingData<Character>> = getCharactersUseCase(
        params = GetCharactersUseCase.GetCharactersParams(
            query = query,
            pagingConfig = getPageConfig()
        )
    ).cachedIn(viewModelScope)

    private fun getPageConfig() = PagingConfig(
        pageSize = 20
    )
}
