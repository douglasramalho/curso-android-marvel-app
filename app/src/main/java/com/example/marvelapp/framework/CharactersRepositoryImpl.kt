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

        // o que fizemos ate agora?
        // fizemos toda a implementacao da nossa camada de dados
        // com base ai no retrofit
        // o que nos precisamos fazer agora?
        // precisamos de uma forma de converter esses dados aqui
        // ou seja com que nossa camada de apresentacao por exemplo o nosso viewModel se comunique
        // com a nossa camada de dados
        // so que eu nao posso diretamente receber um repositorio no meu viewModel
        // na verdade ate posso receber mas exige uma melhor maneira de a gente separar as responsabilidades
        // do nosso aplicativo
        // vamos fazer isso utilizando os casos de uso
        // o que eh um caso de uso?
        // carinha que vai fazer o meio de campo entre duas camadas
        // caso de uso vai executar apenas uma coisa
        // se queremos obter um personagem, vamos ter um caso de uso apenas para obter um personagem
        // e vamos receber no caso de uso um repository
        // e vamos fazer o processo ou seja o que vier da camada de dados nos podemos transformar
        // pra um objeto que fique mais bonitinho da nossa camada de apresentacao, e o que vem
        // da nossa camada de apresentacao eu posso converter os dados que vem de la
        // para os dados que a nossa camada de dados esta esperando


        return CharactersPagingSource(remoteDataSource, query)
    }
}