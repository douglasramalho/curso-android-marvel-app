package com.example.marvelapp.presentation.detail

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.core.usecase.AddFavoriteUseCase
import com.example.core.usecase.CheckFavoriteUseCase
import com.example.core.usecase.RemoveFavoriteUseCase
import com.example.marvelapp.R
import com.example.marvelapp.presentation.detail.FavoriteUiActionStateLiveData.Action.AddFavorite
import com.example.marvelapp.presentation.detail.FavoriteUiActionStateLiveData.Action.RemoveFavorite
import com.example.marvelapp.presentation.detail.FavoriteUiActionStateLiveData.Action.CheckFavorite
import com.example.marvelapp.presentation.detail.FavoriteUiActionStateLiveData.UiState.Icon
import com.example.marvelapp.presentation.detail.FavoriteUiActionStateLiveData.UiState.Loading
import com.example.marvelapp.presentation.detail.FavoriteUiActionStateLiveData.UiState.Error
import com.example.marvelapp.presentation.extensions.watchStatus
import kotlin.coroutines.CoroutineContext

@Suppress("UnusedPrivateMember")
class FavoriteUiActionStateLiveData(
    private val coroutineContext: CoroutineContext,
    private val checkFavoriteUseCase: CheckFavoriteUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
) {

    @set:VisibleForTesting
    var currentFavoriteIcon = R.drawable.ic_favorite_unchecked

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action.switchMap {
        liveData {
            when (it) {
                is CheckFavorite -> {
                    checkFavoriteUseCase.invoke(
                        CheckFavoriteUseCase.Params(it.characterId)
                    ).watchStatus(
                        success = { isFavorite ->
                            if (isFavorite) currentFavoriteIcon = R.drawable.ic_favorite_checked
                            emitFavoriteIcon()
                        },
                        error = {}
                    )
                }
                is AddFavorite -> {
                    it.detailViewArg.run {
                        addFavoriteUseCase.invoke(
                            AddFavoriteUseCase.Params(characterId, name, imageUrl)
                        ).watchStatus(
                            loading = {
                                emit(Loading)
                            },
                            success = {
                                currentFavoriteIcon = R.drawable.ic_favorite_checked
                                emitFavoriteIcon()
                            },
                            error = {
                                emit(Error(R.string.error_add_favorite))
                            }
                        )
                    }
                }
                is RemoveFavorite -> {
                    it.detailViewArg.run {
                        removeFavoriteUseCase.invoke(
                            RemoveFavoriteUseCase.Params(characterId, name, imageUrl)
                        ).watchStatus(
                            loading = {
                                emit(Loading)
                            },
                            success = {
                                currentFavoriteIcon = R.drawable.ic_favorite_unchecked
                                emitFavoriteIcon()
                            },
                            error = {
                                emit(Error(R.string.error_remove_favorite))
                            }
                        )
                    }
                }
            }
        }
    }

    private suspend fun LiveDataScope<UiState>.emitFavoriteIcon() {
        emit(Icon(currentFavoriteIcon))
    }

    fun checkFavorite(characterId: Int) {
        action.value = CheckFavorite(characterId)
    }

    fun update(detailViewArg: DetailViewArg) {
        action.value = if(currentFavoriteIcon == R.drawable.ic_favorite_unchecked){
            AddFavorite(detailViewArg)
        } else RemoveFavorite(detailViewArg)
    }

    sealed class UiState {
        object Loading : UiState()
        data class Icon(@DrawableRes val icon: Int) : UiState()
        data class Error(@StringRes val messageResId: Int) : UiState()
    }

    sealed class Action {
        data class CheckFavorite(val characterId: Int) : Action()
        data class AddFavorite(val detailViewArg: DetailViewArg) : Action()
        data class RemoveFavorite(val detailViewArg: DetailViewArg) : Action()
    }
}