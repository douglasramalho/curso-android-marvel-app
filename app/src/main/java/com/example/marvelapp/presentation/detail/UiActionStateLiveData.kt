package com.example.marvelapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.core.usecase.GetCharacterCategoriesUseCase
import com.example.marvelapp.R
import com.example.marvelapp.presentation.detail.UiActionStateLiveData.Action.Load
import com.example.marvelapp.presentation.extensions.watchStatus
import kotlin.coroutines.CoroutineContext

class UiActionStateLiveData(
    private val coroutineContext: CoroutineContext,
    private val getCharacterCategoriesUseCase: GetCharacterCategoriesUseCase
) {

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action.switchMap {
        liveData(coroutineContext) {
            when (it) {
                is Load -> {
                    getCharacterCategoriesUseCase.invoke(
                        GetCharacterCategoriesUseCase.GetCategoriesParams(it.characterId)
                    ).watchStatus(
                        loading = {
                            emit(UiState.Loading)
                        },
                        success = { data ->
                            val detailParentList = mutableListOf<DetailParentVE>()

                            val comics = data.first
                            if (comics.isNotEmpty()) {
                                comics.map {
                                    DetailChildVE(it.id, it.imageUrl)
                                }.also {
                                    detailParentList.add(
                                        DetailParentVE(R.string.details_comics_category, it)
                                    )
                                }
                            }

                            val events = data.second
                            if (events.isNotEmpty()) {
                                events.map {
                                    DetailChildVE(it.id, it.imageUrl)
                                }.also {
                                    detailParentList.add(
                                        DetailParentVE(R.string.details_events_category, it)
                                    )
                                }
                            }

                            if (detailParentList.isNotEmpty()) {
                                emit(UiState.Success(detailParentList))
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
        action.value = Load(characterId)
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val detailParentList: List<DetailParentVE>) : UiState()
        object Error : UiState()
        object Empty : UiState()
    }

    sealed class Action {
        data class Load(val characterId: Int) : Action()
    }
}