package com.github.coutinhonobre.core.usecase

import androidx.paging.PagingConfig
import com.example.testing.MainCoroutineRule
import com.example.testing.model.CharacterFactory
import com.example.testing.pagingSource.PagingSourceFactory
import com.github.coutinhonobre.core.data.repository.CharactersRepository
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

private const val LIMIT = 20

@RunWith(JUnit4::class)
class GetCharactersUseCaseImplTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var repository: CharactersRepository

    private lateinit var getCharactersUseCase: GetCharactersUseCase

    private val fakePagingSource = PagingSourceFactory.create(
        listOf(
            CharacterFactory.create(CharacterFactory.Hero.ThreeDMan),
            CharacterFactory.create(CharacterFactory.Hero.ABom)
        )
    )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getCharactersUseCase = GetCharactersUseCaseImpl(
            charactersRepository = repository
        )
    }

    @Test
    fun `should validate flow paging data creation when invoke from use case is called`() {
        runTest {
            whenever(repository.getCharacters(""))
                .thenReturn(fakePagingSource)

            val result = getCharactersUseCase(
                GetCharactersUseCase.GetCharactersParams("", PagingConfig(pageSize = LIMIT))
            )

            verify(repository).getCharacters("")

            assertNotNull(result.first())
        }
    }
}
