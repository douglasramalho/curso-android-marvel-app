package com.example.marvelapp.presentation.characters


import androidx.paging.PagingData
import br.com.dio.core.usecase.GetCharactersUseCase
import br.example.lib.MainCoroutineRule
import br.example.lib.model.CharacterFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

//preciso informar qual que vai ser o runner
// o carinha que vai executar esse teste pra mim
// vai me ajudar a rodar o teste, o relatorio de teste
// o carinha que vai fazer isso aqui sera o junit
// runWith eh do pacote runner ou seja o cara que vai rodar o nosso teste
// vamos trabalhar com runner do mockito que esse proprio runner do mockito implementa o runner do junit
// pq utilizamos o runner do mockito?
// isso vai fazer com que as anotacoes que eu utilizar aqui na minha classe de teste.
// vai ser anotacao de mock que vimos na documentacao
// esses objetos que nos vamos anotar com mock eles vao ser automaticamente criados
// quando nos clicarmos no play aqui

@RunWith(MockitoJUnitRunner::class)
class CharactersViewModelTest() {


    // refatoracao
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    // o carinha que queremos testar nao mockamos ele e sim suas dependencias.
    // api Experimental
    // a unica forma de escrever teste utilizando coroutines e flow no nosso codigo
    //eh utilizando essa classe aqui

    //pq que um dispatcher do coroutine aqui eh necessario
    // pq todo o codigo que executamos no coroutine, ele executa em uma thread separada da thread
    // principal, ele vai executar o codigo na thread de IO, na thread default
    // nao temos o controle da thread que esse codigo vai ser executado
    // quando chega aqui no teste precisamos garantir que todo o teste execute numa mesma thread
    // execute de forma sincrona
    // o TestCoroutineDispatcher vem pra colocar todo mundo na mesma thread
//    @ExperimentalCoroutinesApi
//    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    private val charactersFactory = CharacterFactory()

    private val pagingDataCharacters = PagingData.from(
        listOf(
            charactersFactory.create(CharacterFactory.Hero.ThreeDMan),
            charactersFactory.create(CharacterFactory.Hero.ABomb)
        )
    )

    // estou mockando agora uma interface antes era um mock de uma implementacao
    // assim o mockito vai conseguir criar uma instancia desse carinha ja que ele nao eh uma classe
    // final
    // uma instancia vazia
    @Mock
    lateinit var getCharactersUseCase: GetCharactersUseCase

    private lateinit var charactersViewModel: CharactersViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        // ser chamada antes de cada funcao de teste ser executada
        // geralmente usada pra inicializacao
        // isolar o nosso viewModel das nossas dependencias que ele tem
        // deixar os mocks sempre como variaveis publicas
        // o mockito nao consegue mockar classes finais
        // so consegue mockar interfaces que eh o tipo mais facil
        // pq uma interface ele pode criar um objeto fake que implementa aquela interface
        // totalmente vazio
        // codigo conversando na mesma thread
        // refatoracao
//        Dispatchers.setMain(testDispatcher)
        charactersViewModel = CharactersViewModel(getCharactersUseCase)

    }

    // estamos garantindo que nosso viewModel, ele sempre retorna dentro de um flow
    // um paging data corretamente
    // a comunicacao entre o viewModel e o userCase esta ocorrendo de forma correta
    @ExperimentalCoroutinesApi
    @Test
    fun `should validate the paging data object values when calling charactersPagingData`() =
        runBlockingTest {
            // o nosso teste nao conhece nada sobre coroutines
            // ele nao sabe que o nosso charactersViewModel.charactersPagingData fazia uso do nosso
            // coroutines
            // se tivermos uma funcao que trabalhar com coroutines com o uso do runBlockingTest
            // conseguimos trabalhar com coroutines dentro dos testes
            // estamos usando aqui o viewModel real
            // o que vamos mockar sera a resposta do nosso userCase
            // para comparar
            //mockitokotlin2 lib para nao termos problemas com as palavras reservadas do kotlin
            //quando o meu codigo chamar o userCase para qualquer valor que eu passar no invoke
            // seja qualquer valor de query, ou qualqeur valor de paging config, eu vou receber
            // sem problema nenhum.
            // eu vou conseguir fazer essa validao sem problema
            // o que eu estou fazendo
            // o meu userCase
            // retorne pra mim PagingData
            // um flow de pagingData
            // com os dois characters do mock la em cima
            // agora eu vou validar se meu viewModel
            // quando ele executar a minha funcao first eu vou obter o valor do meu viewModel
            // e eu espero receber o valor do meu viewModel
            // que eh o mesmo que o meu userCase retornou aqui.
            whenever(
                getCharactersUseCase.invoke(any())
            ).thenReturn(
                // criar um flow de forma rapida
                flowOf(
                    // mesmo passando que no getUserCase no invoke dele
                    // queremos retonar esse carinha aqui pagingDataCharacters
                    // isso nao vai resolver
                    // pq o userCase cria um objeto aqui dentro
                    // realmente nao conseguiremos valdiar isso
                    // mas a nossa preocupacao maior eh validar se a comunicacao com o nosso viewModel
                    // esta ocorrendo com sucesso com nosso userCase
                    // os dados em si nao importam muito aqui pra gente
                    //
                    pagingDataCharacters
                )
            )

//            val expectedPagingData = pagingDataCharacters
            // tirei .first e ele nao vai executar mais esse flow
            val result = charactersViewModel.charactersPagingData("")
            // como podemos validar dados dentro do pagingData?
            // vamo saber se existe um carinha la dentro
            // se retornar 0 significa que o nosso userCase nao conseguio
            // retornar nada pro nosso ViewModel
            // e nao esta retornando aqui

            // vai dar dois objetos diferentes pq?

            Assert.assertEquals(1, result.count())

            // nosso map nunca vai ser executado e entao o codigo vai sempre passar
//            result.map { resultCharacters ->
//                // ele vai percorrer todos os personagens aqui dentro, e eu posso fazer uma verificao
//                // pra cada resultCharacters eu vou verificar
//                expectedPagingData.map {
//                    // o primeiro paramentro eh o que eu espero
//                    // assim eu vejo se os nomes sao iguals pros dois item da lista que eu tenho
//                    // o meu viewModel depende de uma implementacao completa e nao de uma interface
//                    Assert.assertEquals(it.name, resultCharacters.name)
//                }
//            }
        }

    @ExperimentalCoroutinesApi
    @Test(expected = RuntimeException::class)
    fun `should throw an exception when the calling to the use case return an exception`() =
        runBlockingTest {
            // sempre que eu chamar o meu getCharactersUseCase passando qualquer paramentro
            // eu quero retornar uma exception
            // do tipo Run time exeception
            whenever(getCharactersUseCase.invoke(any()))
                .thenThrow(RuntimeException())

            // eu chamo a funcao para poder chegar dentro do userCase
            // la no userCase eu vou estourar uma exception pq eh isso que eu estou pedindo
            // e pra validar se a exception esta chegando aqui nos caracters viewModel
            // passo aqui dentor do meu @Test expected = RuntimeException::class
            // qual eh a exception que eu espero receber
            //
            charactersViewModel.charactersPagingData("")
        }

    // estamos garantindo que o nosso viewModel esta fazendo a comunicao correta com os nossos casos de uso


//    @ExperimentalCoroutinesApi
//    @After
//    fun tearDownDispatcher(){
//        // pq isso aqui eh necessario quando usamos o dispatchers do coroutines
//        // temos que limpar isso aqui em cada funcao de teste
//        // resetando a cada thread pra evitar efeito colateral nos outros testes
//        Dispatchers.resetMain()
//        // limpamos tambem os testCoroutine
//          refatoracao
//        testDispatcher.cleanupTestCoroutines()
//    }
}
