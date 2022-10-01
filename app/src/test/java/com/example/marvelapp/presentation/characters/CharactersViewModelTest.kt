package com.example.marvelapp.presentation.characters

import androidx.paging.PagingData
import com.example.core.usecase.GetCharactersUseCase
import com.example.testing.MainCoroutineRule
import com.example.testing.model.CharacterFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharactersViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var charactersViewModel: CharactersViewModel

    @Mock
    private lateinit var getCharactersUseCase: GetCharactersUseCase

    private val charactersFactory = CharacterFactory

    private val pagingDataCharacter = PagingData.from(
        listOf(
            charactersFactory.create(CharacterFactory.Hero.ThreeDMan),
            charactersFactory.create(CharacterFactory.Hero.ABom)
        )
    )

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        charactersViewModel = CharactersViewModel(getCharactersUseCase)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should validate the paging data object values when calling charactersPagingData`() =
        runBlockingTest {
            whenever(
                getCharactersUseCase.invoke(
                    any()
                )
            ).thenReturn(
                flowOf(pagingDataCharacter)
            )

            val result = charactersViewModel.charactersPageData("")

            assertEquals(1, result.count())
        }

    @ExperimentalCoroutinesApi
    @Test(expected = RuntimeException::class)
    fun `should throw an exception when the calling to the use case returns an exceptions`() =
        runBlockingTest {
            whenever(
                getCharactersUseCase.invoke(
                    any()
                )
            ).thenThrow(RuntimeException())
            charactersViewModel.charactersPageData("")
        }
}