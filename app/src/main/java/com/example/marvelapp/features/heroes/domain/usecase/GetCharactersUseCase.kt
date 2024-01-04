package com.example.marvelapp.features.heroes.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.marvelapp.commons.utils.usecase.PagingUseCase
import com.example.marvelapp.features.heroes.data.network.repository.CharactersRepository
import com.example.marvelapp.features.heroes.domain.entities.CharacterEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor (
    private val repository: CharactersRepository
): PagingUseCase<GetCharactersUseCase.GetCharactersParams, CharacterEntity>() {
    data class GetCharactersParams(val query: String, val pagingConfig: PagingConfig)

    override fun createFlowObservable(params: GetCharactersParams): Flow<PagingData<CharacterEntity>> {
        return Pager(config = params.pagingConfig) {
            repository.getCharacters(params.query)
        }.flow
    }
}