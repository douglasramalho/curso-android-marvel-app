package br.com.dio.core.data.repository

import androidx.paging.PagingSource
import br.com.dio.core.domain.model.Character

interface CharactersRepository {
    // vamos usar a biblioteca paging do jetPack para fazer a paginacao
    // ela retorna pra outras camadas, pra camada de apresentacao um pagingSource
    // ele espera dois tipos, primeiro e o key que e a representacao do nosso parametro de numero
    // de paginas
    // no nosso projeto vamos trabalhar com offSet se passamos o valor 0 significa que
    // da posicao 0 ate a posicao 20, passando a posicao 20 e da posicao 20 ate a 40
    // isso significa ofsset, na api da marvel esse offset e um numero inteiro
    // o value vai ser o carinha que vamos retornar para nossa camada de apresentacao
    fun getCharacters(query: String): PagingSource<Int, Character>

}