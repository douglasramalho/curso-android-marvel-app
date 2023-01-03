package com.example.marvelapp.presentation.sort

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.core.usecase.GetCharactersSortingUseCase
import com.example.core.usecase.SaveCharactersSortingUseCase
import com.example.core.usecase.base.CoroutinesDispatchers
import com.example.marvelapp.presentation.extensions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class SortViewModel @Inject constructor(
    private val getCharactersSortingUseCase: GetCharactersSortingUseCase,
    private val saveCharactersSortingUseCase: SaveCharactersSortingUseCase,
    private val coroutinesDispatchers: CoroutinesDispatchers
) : ViewModel() {

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action.switchMap { action ->
        liveData(coroutinesDispatchers.main()) {
            when (action) {
                Action.GetStoredSorting -> {
                    getCharactersSortingUseCase.invoke()
                        .collect { sortingPair ->
                            emit(UiState.SortingResult(sortingPair))
                        }
                }
                is Action.ApplySorting -> {
                    val orderBy = action.orderBy
                    val order = action.order

                    saveCharactersSortingUseCase.invoke(
                        SaveCharactersSortingUseCase.Params(orderBy to order)
                    ).watchStatus(
                        loading = {
                            emit(UiState.ApplyState.Loading)
                        },
                        success = {
                            emit(UiState.ApplyState.Success)
                        },
                        error = {
                            emit(UiState.ApplyState.Error)
                        }
                    )
                }
            }
        }
    }

    init {
        action.value = Action.GetStoredSorting
    }

    fun applySorting(orderBy: String, order: String) {
        action.value = Action.ApplySorting(orderBy, order)
    }

    sealed class UiState {
        data class SortingResult(val storedSorting: Pair<String, String>) : UiState()

        sealed class ApplyState : UiState() {
            object Loading : ApplyState()
            object Success : ApplyState()
            object Error : ApplyState()
        }
    }

    sealed class Action {
        object GetStoredSorting : Action()
        data class ApplySorting(
            val orderBy: String,
            val order: String
        ) : Action()
    }
}