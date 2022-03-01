package com.example.marvelapp.presentation.characters

import androidx.paging.PagingData
import br.com.nicolas.core.domain.model.Character
import br.com.nicolas.core.usecase.GetCharactersUseCase
import com.example.testing.MainCoroutineRule
import com.example.testing.model.CharacterFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException

@RunWith(MockitoJUnitRunner::class)
class CharactersViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutine = MainCoroutineRule()

    @Mock
    lateinit var getCharactersUseCase: GetCharactersUseCase

    private lateinit var characterViewModel: CharactersViewModel

    private val characterFactory = CharacterFactory()

    private val pagingDataCharacters = PagingData.from(
        listOf(
            characterFactory.create(CharacterFactory.Hero.ThreeDMan),
            characterFactory.create(CharacterFactory.Hero.ABomb),
        )
    )

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        characterViewModel = CharactersViewModel(getCharactersUseCase)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should validate the paging data object values when calling charactersPagingData`() =
        runBlockingTest {

            whenever(
                getCharactersUseCase.invoke(any())
            ).thenReturn(
                flowOf(
                    pagingDataCharacters
                )
            )

            val result = characterViewModel.characterPagingData("")

            assertEquals(1, result.count())
        }

    @ExperimentalCoroutinesApi
    @Test(expected = RuntimeException::class)
    fun `should throw an exception when te calling to the use case returns an exception`() =
        runBlockingTest {
            whenever(getCharactersUseCase.invoke(any()))
                .thenThrow(RuntimeException())

            characterViewModel.characterPagingData("")
        }

}