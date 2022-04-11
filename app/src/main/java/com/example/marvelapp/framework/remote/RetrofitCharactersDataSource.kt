package com.example.marvelapp.framework.remote

import br.com.dio.core.data.repository.CharactersRemoteDataSource
import com.example.marvelapp.framework.network.MarvelApi
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import javax.inject.Inject


// depende fortemente do marvelApi
// que e o carinha que contem as anotacoes dos metodos http ou seja ele que tem os endpoints
// da nossa api
// como o dagger hilt vai saber criar uma instancia desse RetrofitCharactersDataSource aqui ?
// @Inject
// classes que sao nossas, ou seja implementacoes nossas, precisamos passar apenas o @ Inject
// como estamos utilizando uma interface CharactersRemoteDataSource apenas o @Inject
// nao vai dar certo, pq temos que ensinar o dagger hilt qual implementacao ele tem que injetar
// pra essa interface aqui .
// e pra isso aprendemos que temos que criar um modulo de injecao de dependencia

class RetrofitCharactersDataSource @Inject constructor(
    private val marvelApi: MarvelApi
) : CharactersRemoteDataSource<DataWrapperResponse> {
    // implementacao que esta fortemente ligada ao framework do android
    // se o retrofit for descontinuado basta implementar uma outra fonte de dado
    // vamos supor que iremos trabalhar com ktor
    // o que mudaria seria que receberiamos como parametro a implementacao do ktor ao inves da marvelApi
    // e fariamos um novo dataSource Chamado KtorCharactersDataSource
    // veja que legal
    // como eu defini que meu retorno e um data Wrapper response
    // aqui eu posso utilizar o meu dataWrapperResponse
    // pq o meu fetchCharacters esta esperando um tipo T que nos passamos la na interface
    // o nosso proximo passo e implementar o nosso repository
    override suspend fun fetchCharacters(queries: Map<String, String>): DataWrapperResponse {
        return marvelApi.getCharacters(queries)
    }
}