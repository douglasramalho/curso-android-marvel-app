package com.github.coutinhonobre.core.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.coutinhonobre.core.data.repository.CharactersRepository
import com.github.coutinhonobre.core.domain.model.Character
import com.github.coutinhonobre.core.usecase.base.PagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface GetCharactersUseCase {
    operator fun invoke(params: GetCharactersParams): Flow<PagingData<Character>>

    data class GetCharactersParams(
        val query: String,
        val pagingConfig: PagingConfig
    )
}

class GetCharactersUseCaseImpl @Inject constructor(
    private val charactersRepository: CharactersRepository
) : PagingUseCase<GetCharactersUseCase.GetCharactersParams, Character>(),
GetCharactersUseCase{

    override fun createFlowObservable(params: GetCharactersUseCase.GetCharactersParams): Flow<PagingData<Character>> {
        return Pager(config = params.pagingConfig) {
            charactersRepository.getCharacters(query = params.query)
        }.flow
    }
}
