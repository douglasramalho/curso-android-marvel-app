package br.com.liebersonsantos.core.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.liebersonsantos.core.data.repository.CharactersRepository
import br.com.liebersonsantos.core.domain.model.Character
import br.com.liebersonsantos.core.usecase.GetCharactersUseCase.GetUseCharactersParams
import br.com.liebersonsantos.core.usecase.base.PagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetCharactersUseCase {
    operator fun invoke(params: GetUseCharactersParams): Flow<PagingData<Character>>

    data class GetUseCharactersParams(val query: String, val pagingConfig: PagingConfig)
}

class GetCharactersUseCaseImpl @Inject constructor(
    private val charactersRepository: CharactersRepository
): PagingUseCase<GetUseCharactersParams, Character>(), GetCharactersUseCase {

    override fun createFlowObservable(params: GetUseCharactersParams): Flow<PagingData<Character>> {
        return Pager(config = params.pagingConfig) {
            charactersRepository.getCharacters(params.query)
        }.flow
    }
}