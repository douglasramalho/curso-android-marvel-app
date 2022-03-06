package br.com.nicolas.core.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.nicolas.core.data.repository.CharacterRepository
import br.com.nicolas.core.domain.model.Character
import br.com.nicolas.core.usecase.base.PagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetCharactersUseCase {

    operator fun invoke(params: GetCharactersParams): Flow<PagingData<Character>>

    data class GetCharactersParams(val query: String, val pagingConfig: PagingConfig)
}

class GetCharactersUseCaseImpl @Inject constructor(
    private val characterRepository: CharacterRepository
) : PagingUseCase<GetCharactersUseCase.GetCharactersParams, Character>(), GetCharactersUseCase {

    override fun createFlowObservable(params: GetCharactersUseCase.GetCharactersParams): Flow<PagingData<Character>> {

        val pagingSource = characterRepository.getCharacters(params.query)
        return Pager(config = params.pagingConfig) {
            pagingSource
        }.flow
    }

}