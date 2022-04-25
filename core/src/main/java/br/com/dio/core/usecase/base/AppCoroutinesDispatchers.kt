package br.com.dio.core.usecase.base

import kotlinx.coroutines.CoroutineDispatcher


// estou facilitando aqui
// e fazendo um wrapper
// um container
// de dispatchers de coroutine
// quem precisa de um contexto de coroutine
// eu so passo um instancia desse carinha aqui AppCoroutinesDispatchers
// ai ele vai poder utilizar
data class AppCoroutinesDispatchers(
    val io: CoroutineDispatcher,
    val computation: CoroutineDispatcher,
    val main: CoroutineDispatcher
)
