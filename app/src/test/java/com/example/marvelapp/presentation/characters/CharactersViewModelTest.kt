package com.example.marvelapp.presentation.characters

import androidx.paging.PagingData
import com.example.core.usecase.GetCharactersUseCase
import com.example.testing.MainCoroutinesRule
import com.example.testing.model.CharactersFactory
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
    var mainCoroutineRule = MainCoroutinesRule()

    @Mock
    lateinit var getCharactersUserCase: GetCharactersUseCase

    private lateinit var charactersViewModel: CharactersViewModel

    private val charactersFactory = CharactersFactory()

    private val pagingDataCharacter = PagingData.from(
        listOf(
            charactersFactory.create(CharactersFactory.Hero.ThreeDMan),
            charactersFactory.create(CharactersFactory.Hero.ABomb)
        )
    )

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        charactersViewModel = CharactersViewModel(getCharactersUserCase)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should validate the paging data object values when calling charactersPagingData`() =
        runBlockingTest {
            whenever(
                getCharactersUserCase.invoke(any())
            ).thenReturn(
                flowOf(pagingDataCharacter)
            )

            val result = charactersViewModel.charactersPagingData("")

            assertEquals(1, result.count())
        }

    @ExperimentalCoroutinesApi
    @Test(expected = RuntimeException::class)
    fun `should throw an exception when the calling to the use case returns an exception`() =
        runBlockingTest {
            whenever(
                getCharactersUserCase.invoke(any())
            ).thenThrow(RuntimeException())

            charactersViewModel.charactersPagingData("")
        }

}