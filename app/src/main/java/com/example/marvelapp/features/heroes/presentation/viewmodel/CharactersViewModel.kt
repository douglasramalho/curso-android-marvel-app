package com.example.marvelapp.features.heroes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.marvelapp.commons.utils.Constants.LIMIT
import com.example.marvelapp.features.heroes.domain.entities.CharacterEntity
import com.example.marvelapp.features.heroes.domain.usecase.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val useCase: GetCharactersUseCase
): ViewModel() {

    fun charactersPagingData(query: String): Flow<PagingData<CharacterEntity>> {
        return useCase(
            GetCharactersUseCase.GetCharactersParams(query, getPageConfig())
        ).cachedIn(viewModelScope)
    }

    private fun getPageConfig() = PagingConfig (
        pageSize = LIMIT
    )
}