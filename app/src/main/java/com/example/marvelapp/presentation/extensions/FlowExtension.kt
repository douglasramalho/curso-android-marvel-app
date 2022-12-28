package com.example.marvelapp.presentation.extensions

import com.example.core.usecase.base.ResultStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

suspend fun <T> Flow<ResultStatus<T>>.watchStatus(
    loading: suspend () -> Unit = {},
    success: suspend (data: T) -> Unit,
    error: suspend (throwable: Throwable) -> Unit
) {
    collect { status ->
        when(status) {
            ResultStatus.Loading -> loading.invoke()
            is ResultStatus.Success -> success.invoke(status.data)
            is ResultStatus.Error -> error.invoke(status.throwable)
        }
    }
}