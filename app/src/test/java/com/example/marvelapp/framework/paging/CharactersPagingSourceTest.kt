package com.example.marvelapp.framework.paging

import androidx.paging.PagingSource
import br.com.dio.core.data.repository.CharactersRemoteDataSource
import br.com.dio.core.domain.model.Character
import br.example.lib.MainCoroutineRule
import br.example.lib.model.CharacterFactory
import com.example.marvelapp.factory_response.DataWrapperResponseFactory
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException

@RunWith(MockitoJUnitRunner::class)
class CharactersPagingSourceTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var charactersPagingSource: CharactersPagingSource

    private val dataWrapperResponseFactory = DataWrapperResponseFactory()

    @Mock
    lateinit var remoteDataSource: CharactersRemoteDataSource<DataWrapperResponse>

    private val characterFactory = CharacterFactory()

    @Before
    fun setUp() {
        charactersPagingSource = CharactersPagingSource(remoteDataSource, "")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return a success load result when load is called`() = runBlockingTest {
        // Arrange/Preparo
        whenever(remoteDataSource.fetchCharacters(any()))
            .thenReturn(dataWrapperResponseFactory.create())
        // Action

        val result = charactersPagingSource.load(
            // chamando o paging source pela primeira vez

            PagingSource.LoadParams.Refresh(
                null,
                loadSize = 2,
                false
            )
        )
        // Assert

        // vou criar dois objetos do tipo character
        // da nossa api
        // o nosso response vai ser transformado em nosso objeto do tipo characterModel
        // vamos pegar o nome do imageURl
        // preciso garantir que estao sendo transformados com sucesso.

        val expected = listOf(characterFactory.create(CharacterFactory.Hero.ThreeDMan),
            characterFactory.create(CharacterFactory.Hero.ABomb))
        // temos os dois personagens que queremos verificar se estao dentro do meu resultado de
        // sucesso

        Assert.assertEquals(
            PagingSource.LoadResult.Page(
                // estou criando um objeto que eu quero verificar
                // que eh do mesmo tipo que esta sendo retornando pelo nosso paging source
                data = expected,
                prevKey = null,
                nextKey = 20
            ),
            result
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return a error load result when load is called`() = runBlockingTest {

        // Arrange
        // sempre que remoteDataSource.fetchCharacters
        // qualquer valor explodir uma exeception
        // o que esta ocorrendo aqui?
        // quando ele chegar
        // val response = remoteDateSource.fetchCharacters(queries)
        // ele vai explodir uma exception
        val exception = RuntimeException()
         whenever(remoteDataSource.fetchCharacters(any())).thenThrow(exception)
        // Action
        val result = charactersPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )
        // Assert
        // primeiro parametro e o que eu espero que ocorra
        // blzura o nosso teste passou, garantimos que quando estourar uma exception
        // no nosso fetch characters
        // ele vai retornar essa exception
        // no nosso LoadResult.Error
        Assert.assertEquals(PagingSource.LoadResult.Error<Int,Character>(exception),
            result
        )

    }

}