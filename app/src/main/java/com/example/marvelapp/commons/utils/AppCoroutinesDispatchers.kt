package com.example.marvelapp.commons.utils

import kotlinx.coroutines.CoroutineDispatcher

data class AppCoroutinesDispatchers (
    val io: CoroutineDispatcher,
    val computation: CoroutineDispatcher,
    val main: CoroutineDispatcher
)