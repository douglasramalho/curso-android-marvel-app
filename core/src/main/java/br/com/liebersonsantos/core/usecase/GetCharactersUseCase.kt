package br.com.liebersonsantos.core.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.liebersonsantos.core.data.repository.CharactersRepository
import br.com.liebersonsantos.core.usecase.base.PagingUseCase
import br.com.liebersonsantos.core.domain.model.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
): PagingUseCase<GetCharactersUseCase.GetUseCharactersParams, Character>() {
    override fun createFlowObservable(params: GetUseCharactersParams): Flow<PagingData<Character>> {
        return Pager(config = params.pagingConfig) {
            charactersRepository.getCharacters(params.query)
        }.flow
    }

    data class GetUseCharactersParams(val query: String, val pagingConfig: PagingConfig)
}