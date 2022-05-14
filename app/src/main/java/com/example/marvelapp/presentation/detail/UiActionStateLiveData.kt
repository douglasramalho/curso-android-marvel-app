package com.example.marvelapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.marvelapp.R
import com.example.marvelapp.presentation.extensions.watchStatus
import usecase.GetCharacterCategoriesUseCase
import kotlin.coroutines.CoroutineContext

class UiActionStateLiveData(
    private val coroutineContext: CoroutineContext,
    private val getCharacterCategoriesUseCase: GetCharacterCategoriesUseCase
) {

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action.switchMap {
        liveData(coroutineContext) {
            when (it) {
                is Action.Load -> {
                    getCharacterCategoriesUseCase.invoke(
                        GetCharacterCategoriesUseCase.GetCategoriesParams(it.characterId)
                    ).watchStatus(
                        loading = {
                            emit(UiState.Loading)
                        },
                        success = { data ->
                            val detailParentLis = mutableListOf<DetailParentsVE>()

                            val comics = data.first
                            if (comics.isNotEmpty()) {
                                comics.map {
                                    DetailChildVE(it.id, it.imageUrl)
                                }.also {
                                    detailParentLis.add(
                                        DetailParentsVE(R.string.details_comics_category, it)
                                    )
                                }
                            }
                            val event = data.second
                            if (event.isNotEmpty()) {
                                event.map {
                                    DetailChildVE(it.id, it.imageUrl)
                                }.also {
                                    detailParentLis.add(
                                        DetailParentsVE(R.string.details_event_category, it)
                                    )
                                }
                            }
                            if (detailParentLis.isNotEmpty()) {
                                emit(UiState.Success(detailParentLis))
                            } else emit(UiState.Empty)
                        },
                        error = {
                            emit(UiState.Error)
                        }
                    )
                }
            }
        }
    }

    fun load(characterId: Int) {
        action.value = Action.Load(characterId)
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val detailParentList: List<DetailParentsVE>) : UiState()
        object Error : UiState()
        object Empty : UiState()
    }

    sealed class Action {
        data class Load(val characterId: Int) : Action()
    }
}