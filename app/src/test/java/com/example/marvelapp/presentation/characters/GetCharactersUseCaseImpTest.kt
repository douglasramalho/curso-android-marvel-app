package com.example.marvelapp.presentation.characters

import androidx.paging.PagingConfig
import br.com.dio.core.data.repository.CharactersRepository
import br.com.dio.core.usecase.GetCharactersUseCase
import br.com.dio.core.usecase.GetCharactersUseCaseImp
import br.example.lib.MainCoroutineRule
import br.example.lib.model.CharacterFactory
import br.example.lib.pagingsource.PagingSourceFactory
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetCharactersUseCaseImpTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // quando o runner do mockito
    // executar o nosso teste
    // ele vai ver que existe uma anotacao mock
    // ele vai cuidar de instanciar pra mim
    // essa variavel aqui como objeto fake
    // e podemos controlar como queremos esse objeto
    @Mock
    lateinit var repository: CharactersRepository

    // qual que eh o objeto que eu vou esta testando nesse momento.
    // aqui eu posso depender da nossa interfac direto e nao do nosso imp
    private lateinit var getCharactersUseCase: GetCharactersUseCase

    private val hero = CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)
    private val fakePagingSource = PagingSourceFactory().create(listOf(hero))

    @Before
    fun setUp() {
        // teste executado antes de cada codigo
        // preciso criar as minhas dependencias
        // o meu GetCharactersUseCaseImp depende de um repository
        // que eh uma interface
        // se vc fizer com que seu codigo dependa de uma abstracao e nao de uma implementacao
        // classe vs interface
        // sua vida ficara mais facil nos testes unitarios
        // interface conseguimos fazer mock com mockito
        // precisamos testar o GetCharactersUseCaseImp de forma isolada
        // sem depender de ng
        // sem nenhuma dependencia externa
        // quero ter total controle sobre o meu repository pra testar meu user case de forma isolada

        getCharactersUseCase = GetCharactersUseCaseImp(repository)
//        MockitoAnnotations.initMocks(this)
        // caso vc use outro runner sem ser o MockitoJUnitRunner
        // e quiser ainda utilizar a anotacao @Mock
        // vc pode fazer essa linha de codigo acima.
        //
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should validate flow paging data creation when invoke from use case is call`() =
        runBlockingTest {
            whenever(repository.getCharacters(""))
                .thenReturn(fakePagingSource)

            // so consigo fazer verify em objetos criados pelo mockito
            val result = getCharactersUseCase
                .invoke(GetCharactersUseCase.GetCharactersParams("", PagingConfig(20)))
            // o verify verifica se o repositorio foi chamado como tambem o get characters
            // posso passar uma vez pois por padrao
            verify(repository).getCharacters("")

            Assert.assertNotNull(result.first())
            // o first vai retornar o paging data
            // e sabemos que nao conseguimos fazer nada com paging data
            // nenhuma verificacao
            // garantir que paging data nao seja vazio
            // pq esse flow eh um hot flow pq eh flow que esta sendo retornado
            // se for retornado outro tipo de objeto que nao seja paging data
            // o nosso teste vai quebrar

        }
}