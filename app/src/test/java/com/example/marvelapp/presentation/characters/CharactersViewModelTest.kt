package com.example.marvelapp.presentation.characters

import androidx.paging.PagingData
import com.github.coutinhonobre.core.domain.model.Character
import com.github.coutinhonobre.core.usecase.GetCharactersUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

private const val PAGING_SEARCH = ""

@RunWith(MockitoJUnitRunner::class)
class CharactersViewModelTest {

    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = UnconfinedTestDispatcher(testScheduler)
    private val testScope = TestScope(testDispatcher)

    @Mock
    lateinit var getCharactersUseCase: GetCharactersUseCase

    private lateinit var charactersViewModel: CharactersViewModel

    private val pagingDataCharacters = PagingData.from(
        listOf(
            Character(
                name = "3-D Man",
                imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg"
            ),
            Character(
                name = "A-Bomb (HAS)",
                imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16.jpg"
            )
        )
    )

    @Before
    fun setUp() {
        charactersViewModel = CharactersViewModel(
            getCharactersUseCase = getCharactersUseCase
        )
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should validate the paging data object values when calling charactersPagingData`() =
        testScope.runTest {

            whenever(
                getCharactersUseCase(any())
            ).thenReturn(
                flowOf(
                    pagingDataCharacters
                )
            )
            val result = charactersViewModel.charactersPagingData(
                query = PAGING_SEARCH
            )
            assertNotNull(result.first())

        }

    @ExperimentalCoroutinesApi
    @Test(expected = RuntimeException::class)
    fun `should throw an exception when the calling to the use case returns an exception`() =
        testScope.runTest {
            whenever(getCharactersUseCase(any()))
                .thenThrow(RuntimeException())
            
            charactersViewModel.charactersPagingData(
                query = PAGING_SEARCH
            )
        }
}
