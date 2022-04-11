package com.example.marvelapp.framework

import androidx.paging.PagingSource
import br.com.dio.core.data.repository.CharactersRemoteDataSource
import br.com.dio.core.data.repository.CharactersRepository
import br.com.dio.core.domain.model.Character
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import com.example.marvelapp.framework.paging.CharactersPagingSource
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
        // o que a biblioteca paging 3 nos ajuda
        // ela nos ajuda a carregar e exibir paginas de um conjuto de dados maior do armazenamento local ou da rede
        // permitindo que app use a largura de banda da rede e os recursos do sistema de modo mais eficiente.
        // por exemplo api tem 1500 dados, nao e legal trazer esses dados todos de uma vez


        return CharactersPagingSource(remoteDataSource, query)
    }
}