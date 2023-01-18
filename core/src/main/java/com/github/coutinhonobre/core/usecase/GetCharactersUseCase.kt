package com.github.coutinhonobre.core.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.coutinhonobre.core.data.repository.CharactersRepository
import com.github.coutinhonobre.core.domain.model.Character
import com.github.coutinhonobre.core.usecase.base.PagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) : PagingUseCase<GetCharactersUseCase.GetCharactersParams, Character>() {

    override fun createFlowObservable(params: GetCharactersParams): Flow<PagingData<Character>> {
        return Pager(config = params.pagingConfig) {
            charactersRepository.getCharacters(query = params.query)
        }.flow
    }

    data class GetCharactersParams(
        val query: String,
        val pagingConfig: PagingConfig
    )
}
