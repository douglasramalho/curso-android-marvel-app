package com.example.marvelapp.framework

import androidx.paging.PagingSource
import br.com.dio.core.data.repository.CharactersRemoteDataSource
import br.com.dio.core.data.repository.CharactersRepository
import br.com.dio.core.domain.model.Character
import com.example.marvelapp.framework.netowork.response.DataWrapperResponse
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharactersRemoteDataSource<DataWrapperResponse>
) : CharactersRepository {
    override fun getCharacters(query: String): PagingSource<Int, Character> {
        // o que estamos fazendo aqui ?
        // estamos fazendo o nosso CharactersRepositoryImpl depender de uma abstracao
        // e nao de uma implementacao, hora nenhuma o meu repository aqui sabe que e o retrofit
        // que esta implementando essa fonte de dados remoto, sendo assim caso eu implemente no futuro
        // uma outra fonte de dados, nao importa o repository ele so quer saber de obter as funcoes
        // que foram definidas la no contrato
        return CharactersPaging()
    }
}