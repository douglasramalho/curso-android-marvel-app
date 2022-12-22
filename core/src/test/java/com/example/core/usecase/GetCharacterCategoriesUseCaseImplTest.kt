package com.example.core.usecase

import com.example.core.data.repository.CharactersRepository
import com.example.core.usecase.GetCharacterCategoriesUseCase.GetCategoriesParams
import com.example.core.usecase.base.ResultStatus
import com.example.testing.MainCoroutinesRule
import com.example.testing.model.CharacterFactory
import com.example.testing.model.ComicFactory
import com.example.testing.model.EventFactory
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetCharacterCategoriesUseCaseImplTest {

    @get:Rule
    var mainCoroutinesRule = MainCoroutinesRule()

    private val character = CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)
    private val comics = listOf(ComicFactory().create(ComicFactory.FakeComic.FakeComic1))
    private val events = listOf(EventFactory().create(EventFactory.FakeEvent.FakeEvent1))

    @Mock
    private lateinit var repository: CharactersRepository

    private lateinit var getCharacterCategoriesUseCase: GetCharacterCategoriesUseCase

    @Before
    fun setUp() {
        getCharacterCategoriesUseCase = GetCharacterCategoriesUseCaseImpl(
            repository,
            mainCoroutinesRule.testDispatcherProvider
        )
    }

    @Test
    fun `should return Success from ResultStates when get both requests return success`() =
        runTest {
            // Arrange
            whenever(repository.getComics(character.id)).thenReturn(comics)
            whenever(repository.getEvents(character.id)).thenReturn(events)

            // Atc
            val result = getCharacterCategoriesUseCase.invoke(GetCategoriesParams(character.id))

            // Assert
            val resultList = result.toList()
            assertEquals(ResultStatus.Loading, resultList[0])
            assertTrue(resultList[1] is ResultStatus.Success)
        }

    @Test
    fun `should return Error from ResultStates when get events requests return error`() =
        runTest {
            whenever(repository.getComics(character.id)).thenAnswer { throw Throwable() }

            // Atc
            val result = getCharacterCategoriesUseCase.invoke(GetCategoriesParams(character.id))

            // Assert
            val resultList = result.toList()
            assertEquals(ResultStatus.Loading, resultList[0])
            assertTrue(resultList[1] is ResultStatus.Error)
        }

    @Test
    fun `should return Error from ResultStates when get comics requests return error`() =
        runTest {
            whenever(repository.getComics(character.id)).thenReturn(comics)
            whenever(repository.getEvents(character.id)).thenAnswer { throw Throwable() }

            // Atc
            val result = getCharacterCategoriesUseCase.invoke(GetCategoriesParams(character.id))

            // Assert
            val resultList = result.toList()
            assertEquals(ResultStatus.Loading, resultList[0])
            assertTrue(resultList[1] is ResultStatus.Error)
        }
}