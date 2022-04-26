package com.example.marvelapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.Comic
import com.example.core.domain.model.Event
import com.example.marvelapp.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import usecase.GetCharacterCategoriesUseCase
import usecase.base.ResultStatus
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCharacterCategoriesUseCase: GetCharacterCategoriesUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    fun getCharacterCategories(characterId: Int) = viewModelScope.launch {
        getCharacterCategoriesUseCase.invoke(
            GetCharacterCategoriesUseCase.GetCategoriesParams(characterId)
        ).watchStatus()
    }

    private fun Flow<ResultStatus<Pair<List<Comic>, List<Event>>>>.watchStatus() =
        viewModelScope.launch {
            collect { status ->
                _uiState.value = when (status) {
                    ResultStatus.Loading -> UiState.Loading
                    is ResultStatus.Success -> {
                        val detailParentLis = mutableListOf<DetailParentsVE>()

                        val comics = status.data.first
                        if (comics.isNotEmpty()) {
                            comics.map {
                                DetailChildVE(it.id, it.imageUrl)
                            }.also {
                                detailParentLis.add(
                                    DetailParentsVE(R.string.details_comics_category, it)
                                )
                            }
                        }
                        val event = status.data.second
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
                            UiState.Success(detailParentLis)
                        } else UiState.Empty
                    }
                    is ResultStatus.Error -> UiState.Error
                }
            }
        }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val detailParentList: List<DetailParentsVE>) : UiState()
        object Error : UiState()
        object Empty : UiState()
    }
}