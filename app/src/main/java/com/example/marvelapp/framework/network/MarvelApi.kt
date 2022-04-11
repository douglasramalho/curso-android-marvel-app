package com.example.marvelapp.framework.network

import com.example.marvelapp.framework.network.response.DataWrapperResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MarvelApi {
    @GET("characters")
    // ele automaticamente vai fazer o parser utilizando gson
    // como nos informamos pro retrofit pra ele poder fazer o mapeamento automatico
    // converter esse metodo aqui do kotlin pra um querie parameter dentro do url
    // temos que passar aqui essa anotacao
    // o retrofit automaticamente vai converter esses dados aqui
    suspend fun getCharacters(
        @QueryMap
        queries: Map<String, String>
    ): DataWrapperResponse
}