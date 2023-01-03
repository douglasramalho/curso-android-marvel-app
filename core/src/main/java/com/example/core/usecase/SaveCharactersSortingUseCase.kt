package com.example.core.usecase

import com.example.core.data.mapper.SortingMapper
import com.example.core.data.repository.FavoritesRepository
import com.example.core.data.repository.StorageRepository
import com.example.core.domain.model.Character
import com.example.core.usecase.AddFavoriteUseCase.Params
import com.example.core.usecase.base.CoroutinesDispatchers
import com.example.core.usecase.base.ResultStatus
import com.example.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface SaveCharactersSortingUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>

    data class Params(val sortingPair:  Pair<String, String>)
}

class SaveCharactersSortingUseCaseImpl @Inject constructor(
    private val storageRepository: StorageRepository,
    private val sortingMapper: SortingMapper,
    private val dispatchers: CoroutinesDispatchers
) : UseCase<SaveCharactersSortingUseCase.Params, Unit>(), SaveCharactersSortingUseCase {

    override suspend fun doWork(params: SaveCharactersSortingUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()){
            storageRepository.saveSorting(sortingMapper.mapFromPair(params.sortingPair))
            ResultStatus.Success(Unit)
        }
    }
}