package com.example.marvelapp.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.liebersonsantos.core.usecase.GetCharactersUseCase
import kotlinx.coroutines.flow.Flow
import br.com.liebersonsantos.core.domain.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
): ViewModel() {

    fun charactersPagingData(query: String): Flow<PagingData<Character>> {
        return getCharactersUseCase(
            GetCharactersUseCase.GetUseCharactersParams(query, getPagingConfig())
        ).cachedIn(viewModelScope)
    }

    private fun getPagingConfig() = PagingConfig(pageSize = 20)
}