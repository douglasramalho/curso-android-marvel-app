package com.example.marvelapp.presentation.characters

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.core.domain.model.Character
import com.example.core.usecase.GetCharactersUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharactersViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var charactersViewModel: CharactersViewModel

    @Mock
    lateinit var getCharactersUseCase: GetCharactersUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        charactersViewModel = CharactersViewModel(getCharactersUseCase)
    }

    private val pagingDataCharacters = PagingData.from(
        listOf(
            Character(
                "3-D Man",
                "https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg"
            ),
            Character(
                "A-bomb (HAS)",
                "https://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16.jpg"
            )
        )
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should validate the paging data object values when calling charactersPagingData`() = runBlockingTest {
        whenever(getCharactersUseCase.invoke(
            any()
//            GetCharactersUseCase.GetCharactersParams("", PagingConfig(20))
        )).thenReturn(
            flowOf(pagingDataCharacters)
        )

        val result = charactersViewModel.charactersPagingData("")

        assert(result.count() == 1)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test(expected = RuntimeException::class)
    fun `should throw an exception when the calling to the use case returns an exception`() = runBlockingTest {
        whenever(getCharactersUseCase.invoke(any()))
            .thenThrow(RuntimeException())

        charactersViewModel.charactersPagingData("")
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

}