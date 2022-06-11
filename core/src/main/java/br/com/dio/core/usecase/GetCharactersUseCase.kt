package br.com.dio.core.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.dio.core.data.repository.CharactersRepository
import br.com.dio.core.domain.model.Character
import br.com.dio.core.usecase.GetCharactersUseCase.GetCharactersParams
import br.com.dio.core.usecase.base.PagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// o nosso userCase e o carinha que vai fazer a separacao da camada de apresentacao da nossa
// camada de dados
// por isso que aqui eu vou receber o CharactersRepository
// sempre usar o repository
// assim eu nao dependo de uma implementacao
// so consigo depender de uma abstracao o contrato
// o nosso caso de uso representa a regra de negocio da nossa aplicacao


// ensinamos o dagger como criar uma instancia desse carinha aqui

interface GetCharactersUseCase {
    operator fun invoke(params: GetCharactersParams): Flow<PagingData<Character>>

    data class GetCharactersParams(val query: String, val pagingConfig: PagingConfig)

}
// temos que ensinar o dagger hilt como injetar uma implementacao desse carinha aqui GetCharactersUseCaseImp


class GetCharactersUseCaseImp @Inject constructor(
    private val charactersRepository: CharactersRepository
) : PagingUseCase<GetCharactersParams, Character>(),
    GetCharactersUseCase {
    // aqui no meu GetCharactersUseCase eu vou receber um pagingConfig implementado
    // que representa esse pager, esses sao os parametros que meu viewModel tem que passar
    // nao preciso converter o personagem que eh da camada de domain para um objeto da camada de view
    // pq se nao a aplicacao fica muito burocratica
    // a minha view a unica coisa que ela conhece e a entidade Character que esta na camada
    // de domain

    // veja que legal o nosso GetCharactersUseCase tem uma unica responsabilidade ou unica funcao
    // que contem regra de negocio da nossa aplicacao e nao da nossa empresa
    // regra de negocio da aplicacao trabalha com paging 3
    // entao futuramente se for o caso de mudar eu apenas implemento com base na nova lib que eu
    // for utilizar

    override fun createFlowObservable(params: GetCharactersParams): Flow<PagingData<Character>> {
        // ele esta so instanciando o pager e retornando um flow desse pager
        // se eu colocar um breakPoint charactersRepository.getCharacters(params.query)
        // nao vamos conseguir entrar nessa linha de codigo
        // pq essa linha de codigo esta dentro desse lambda
        // no momento que meu pager for criado
        // e for pegar os characters do nosso repository
        // esse carinha aqui charactersRepository.getCharacters(params.query) vai retornar null
        // pq nao fornecemos nenhum retorno em nosso teste
        // pq a instancia do nosso repository feita pelo mock
        // e vazia @Mock
        val pagingSource = charactersRepository.getCharacters(params.query)
        // deixei ela como astrata pra forcar quem esta implementando essa funcao a definir como ela
        return Pager(config = params.pagingConfig) {  // abro essa funcao anonima ou bloco
            // ele sempre cria uma instancia nova de pager e isso eh um problema pros testes
            // toda vida ele cria uma instancia quando passa pelo flow e retorna essa nova instancia
//            charactersRepository.getCharacters(params.query)
            pagingSource
            // sabemos que o getCharacters retorna um pagingSource
            // dentro aqui do nosso Pager ele espera que seja passado aqui um carinha
            // do tipo Paging Source
        }.flow
        // estamso retornando um flow de pagingData de um valor qualquer que no nosso caso
        // eh Flow<PagingData<Character>>
        // a biblioteca nos orienta a converter pagingSource aqui em um fluxo de dados
        // que vai ser utilizado pela nossa camada de apresentacao
        // assim o nosso adaptador, recyclerView consiga ficar escutando esse fluxo de dados
        // para que ele possa fazer novas requests e receber novos dados e popular o nosso recycler
        // de forma automatica
        // quem for chamar essa funcao vai ta de fato observando um flow ou fluxo de dados do paging

    }

//    data class GetCharactersParams(val query: String, val pagingConfig: PagingConfig)

    // na arquitetura do paging 3 vemos que precisamos na camada do viewModel
    // ter um Pager

}