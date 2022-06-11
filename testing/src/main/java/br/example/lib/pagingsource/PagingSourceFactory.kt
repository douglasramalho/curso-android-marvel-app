package br.example.lib.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.dio.core.domain.model.Character

class PagingSourceFactory {

    fun create(heroes: List<Character>) = object : PagingSource<Int, Character>(){
        override fun getRefreshKey(state: PagingState<Int, Character>) = 1

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
            // vai sempre retornar um sucesso
            // eu chamo o theReturn para retornar um sucesso
            // e com base no sucesso a minha classe de destino a minha classe que eu estou testando
            // tem que fazer
            // quando eu chamo essa mesma funcao
            // eu quero retornar um erro pra ver o que minha funcao faz
            // estamos criando o nosso paging source na mao
            // uma interface nos ajuda muito a executar esse tipo de trabalho
            // se na empresa nao tiver mockito vc precisa criar as dependencias na mao
            return LoadResult.Page(
                data = heroes,
                prevKey = null,
                nextKey = 20
            )
        }
    }
}