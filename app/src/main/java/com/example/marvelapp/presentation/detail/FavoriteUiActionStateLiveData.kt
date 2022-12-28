package com.example.marvelapp.presentation.detail

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.core.usecase.AddFavoriteUseCase
import com.example.marvelapp.R
import com.example.marvelapp.presentation.detail.FavoriteUiActionStateLiveData.Action.Update
import com.example.marvelapp.presentation.detail.FavoriteUiActionStateLiveData.Action.Default
import com.example.marvelapp.presentation.detail.FavoriteUiActionStateLiveData.UiState.Icon
import com.example.marvelapp.presentation.detail.FavoriteUiActionStateLiveData.UiState.Loading
import com.example.marvelapp.presentation.detail.FavoriteUiActionStateLiveData.UiState.Error
import com.example.marvelapp.presentation.extensions.watchStatus
import kotlin.coroutines.CoroutineContext

@Suppress("UnusedPrivateMember")
class FavoriteUiActionStateLiveData(
    private val coroutineContext: CoroutineContext,
    private val addFavoriteUseCase: AddFavoriteUseCase
) {

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action.switchMap {
        liveData {
            when (it) {
                Default -> emit(Icon(R.drawable.ic_favorite_unchecked))
                is Update -> {
                    it.detailViewArg.run {
                        addFavoriteUseCase.invoke(
                            AddFavoriteUseCase.Params(characterId, name, imageUrl)
                        ).watchStatus(
                            loading = {
                                emit(Loading)
                            },
                            success = {
                                emit(Icon(R.drawable.ic_favorite_checked))
                            },
                            error = {
                                emit(Error(R.string.error_add_favorite))
                            }
                        )
                    }
                }
            }
        }
    }

    fun setDefault() {
        action.value = Default
    }

    fun update(detailViewArg: DetailViewArg) {
        action.value = Update(detailViewArg)
    }

    sealed class UiState {
        object Loading : UiState()
        data class Icon(@DrawableRes val icon: Int) : UiState()
        data class Error(@StringRes val messageResId: Int) : UiState()
    }

    sealed class Action {
        object Default : Action()
        data class Update(val detailViewArg: DetailViewArg) : Action()
    }
}