package com.example.marvelapp.presentation.characters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import androidx.paging.map
import br.com.liebersonsantos.core.domain.model.Character
import br.com.liebersonsantos.core.usecase.GetCharactersUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class CharactersViewModelTest {

    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var getCharactersUseCase: GetCharactersUseCase

    private lateinit var charactersViewModel: CharactersViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        charactersViewModel = CharactersViewModel(getCharactersUseCase)
    }

    @Test
    fun `should validate the paging data object values when calling characters paging data`() =
        runBlockingTest() {

            whenever(getCharactersUseCase.invoke(any())).thenReturn(flowOf(pagingDataCharacters))

            val expectedPagingData = pagingDataCharacters

            val result = charactersViewModel.charactersPagingData("").first()

            result.map { resultCharacter ->
                expectedPagingData.map {
                    assertEquals(it.name, resultCharacter.name)
                }

            }
        }

    @Test(expected = RuntimeException::class)
    fun `should throw an exception when the calling to the use case returns an exception`() =
        runTest {
            whenever(getCharactersUseCase.invoke(any()))
                .thenThrow(RuntimeException())

            charactersViewModel.charactersPagingData("")
        }

    private val pagingDataCharacters = PagingData.from(
        listOf(
            Character("3-D Man", "https://i.annihil.us/u/prod/marvel/i/mg/c/e0/536fecbbb9784.jpg"),
            Character("3-D Man", "https://i.annihil.us/u/prod/marvel/i/mg/c/e0/536fecbbb9784.jpg")
        )
    )
}