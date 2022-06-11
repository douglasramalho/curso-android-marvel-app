package com.example.marvelapp.framework.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.dio.core.data.repository.CharactersRemoteDataSource
import br.com.dio.core.domain.model.Character
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import com.example.marvelapp.framework.network.response.toCharacterModel

// vou passar um key e um value
// vou passar um key inteiro pq vimos na documentacao que o key trabalha com tipo inteiro
// e o value vou passar o nosso tipo Character
// query nome de um personagem que o usuario pode digitar na camada de ui para jogar pra ca onde
// poderemos filtrar
// nao vou testar o nosso repository
// e nem o nosso data source
// pq o que eles fazem e apenas retornar
// uma linha de codigo
// e eu nao quero perder meu tempo testando apenas uma linha de codigo
class CharactersPagingSource(
    private val remoteDateSource: CharactersRemoteDataSource<DataWrapperResponse>,
    private val query: String
) : PagingSource<Int, Character>() {
    @Suppress("TooGenericExceptionCaught")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        // aqui dentro que vamos ter os detalhes de implementacao
        // lembrando o paging trabalha com coroutine entao ele automaticamente trouxe funcoes com
        // suspend
        return try {
            // como coroutine e uma funcionalidade do kotlin eu posso trabalhar tambem com try catch
            val offset = params.key ?: 0
            // ele vai retornar esse carinha pra mim que eh do tipo inteiro
            // como funciona a biblioteca do paging
            // quando ele fizer uma request ou seja vamos esta instanciando o pagging source
            // vamos esta fazendo a primeira request
            // esse key aqui por padrao aqui vai me retornar nulo
            // pq ele nao sabe qual eh a proxima pagina que tem ser solicitada
            // ou seja na primeira vez que cai na funcao load esse carinha key vai ta nulo
            // como ele vai ta nulo vou fazer uma tratativa
            // se for nulo ele vai iniciar com 0
            // ou seja nosso offset sempre inicia com 0

            // estou montando os meus queries parameter
            // estamos definindo todos os parametros que vamos usar na nossa url
            val queries = hashMapOf(
                "offset" to offset.toString()
            )

            if (query.isNotEmpty()) {
                queries["nameStartWith"] = query
                // to criando uma nova chave
            }

            val response = remoteDateSource.fetchCharacters(queries)
            // precisamos do offsset e do total para saber qual proxima pagina carregar
            val responseOffset = response.data.offset
            val totalCharacters = response.data.total
            // como o paging funciona dentro do loading aqui ?
            // ele espera que nos retornemos um LoadResult
            // se ele fizer a request e nao estourar nenhum erro aqui

            LoadResult.Page(
                // ele vai pegar o response.results.map
                // e vai transformar no nosso modelo
                data = response.data.results.map { it.toCharacterModel()
                    // usei o map para converter de CharacterResponse para
                    // quando eu faco o map aqui eu estou percorrendo todos os meus resultados aqui
                    // fizemos a conversao do objeto de response para o nosso objeto de domain
                    // e estamos aqui retornando no parametro data o tipo correto o tipo esperado


                },
                // como nao vamos estar trabalhando na posicao inversa
                //  vamos estar solicitando dados apartir da ultima posicao da lista
                // temos que calcular agora o proximo offset
                // o nosso offset representa a quantidade de personagens que nos ja temos
                // no nosso aplicativo, ou seja se nosso offset for 0
                // e nos tivermos 1000 characters ele vai cair nessa condicao
                // e vai trazer a proxima pagina
                // 20 e menor que 1000
                // sempre que ele preencher o next key com o novo valor
                // se vc fizer um calculo errado ou nao verificar isso aqui
                // se ele nao conseguir fazer alguma verificao aqui ele vai ficar chamando
                // novas paginas sem trazer resultado
                // request desnecessarias
                // sabemos que estamos obtendo personagens de 20 em 20
                prevKey = null,
                // no nosso teste eu quero validar, se o next key eh igual 20
                // como tambem se o data tem 2 resultados
                nextKey = if (responseOffset < totalCharacters) {
                    responseOffset + LIMIT
                } else null
                // aqui ele para de fazer request para api
            )

        } catch (exception: Exception) {
            // eu nao quero especificar o erro que vai ser capturado aqui
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        // basicamente ele vai entrar em acao quando precisamos invalidar o nosso adapter por algum
        // motivo swipe refresh
        // quando o nosso aplicativo for morto em background
        // quando o sistema operacional teve que matar o nosso app
        // o estado vai ser salvo nesse state
        // o app vai tentar recuperar o state quando o usuario tentar voltar pro app
        // e com base nesse estado aqui ele vai tentar recuperar qual que e a pagina que estava sendo
        // exibida pro usuario para poder continuar a paginacao

        return state.anchorPosition?.let { anchorPosition ->
            // tenta recuperar o stado com base numa posicao ancora que ele guardou
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LIMIT) ?: anchorPage?.nextKey?.minus(LIMIT)
        }
    }

    companion object {
        private const val LIMIT = 20
    }
}