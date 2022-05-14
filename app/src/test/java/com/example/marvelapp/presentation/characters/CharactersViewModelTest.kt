package com.example.marvelapp.presentation.characters

import androidx.paging.PagingData
import com.example.testing.MainCoroutineRule
import com.example.testing.model.CharactersFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import usecase.GetCharactersUseCase

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharactersViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

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

    @Before
    fun setUp() {
        charactersViewModel =
            CharactersViewModel(getCharactersUserCase, mainCoroutineRule.testDispatcherProvider)
    }

    @Test
    fun `should validate the paging data object values when calling charactersPagingData`() =
        runTest {
            whenever(
                getCharactersUserCase.invoke(any())
            ).thenReturn(
                flowOf(pagingDataCharacter)
            )

            val result = charactersViewModel.charactersPagingData("")

            assertNotNull(result.first())
        }

    @Test(expected = RuntimeException::class)
    fun `should throw an exception when the calling to the use case returns an exception`() =
        runTest {
            whenever(
                getCharactersUserCase.invoke(any())
            ).thenThrow(RuntimeException())

            charactersViewModel.charactersPagingData("")
        }
}