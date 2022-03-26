package com.example.marvelapp.presentation.characters

import androidx.paging.PagingData
import com.example.core.usecase.GetCharactersUseCase
import com.example.testing.MainCoroutineRule
import com.example.testing.model.CharacterFactory
import com.example.testing.model.CharacterFactory.Hero.ABomb
import com.example.testing.model.CharacterFactory.Hero.ThreeDMan
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharactersViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var getCharactersUseCase: GetCharactersUseCase
    private lateinit var charactersViewModel: CharactersViewModel

    private val caracterFactory = CharacterFactory()
    private val pagingDataCharacters = PagingData.from(
        listOf(
            caracterFactory.create(ThreeDMan),
            caracterFactory.create(ABomb),
        )
    )

    @Before
    fun setUp() {
        charactersViewModel = CharactersViewModel(getCharactersUseCase)
    }

    @Test
    fun `should validate the paging data object values when calling charactersPagingData`() =
        runBlockingTest {
            whenever(getCharactersUseCase.invoke(any())).thenReturn(
                flowOf(
                    pagingDataCharacters
                )
            )
            val result = charactersViewModel.charactersPagingData("")
            assertEquals(1, result.count())
        }

    @Test(expected = RuntimeException::class)
    fun `should throw an exception when the calling to the usecase returns an exception`() =
        runBlockingTest {
            whenever(getCharactersUseCase.invoke(any())).thenThrow(RuntimeException())
            charactersViewModel.charactersPagingData("")
        }
}