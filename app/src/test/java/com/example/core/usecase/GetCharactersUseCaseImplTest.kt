package com.example.core.usecase

import androidx.paging.PagingConfig
import com.example.core.data.repository.CharactersRepository
import com.example.testing.MainDispatcherRule
import com.example.testing.model.CharacterFactory
import com.example.testing.pagingsoruce.PagingSourceFactory
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GetCharactersUseCaseImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var repository: CharactersRepository

    private lateinit var getCharactersUseCase: GetCharactersUseCase

    private val hero = CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)

    private val fakePagingSource = PagingSourceFactory().create(listOf(hero))

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getCharactersUseCase = GetCharactersUseCaseImpl(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should validate flow paging data creation when invoke form use case is called`() =
        runTest {
            whenever(repository.getCharacters(""))
                .thenReturn(fakePagingSource)

            val result = getCharactersUseCase
                .invoke(GetCharactersUseCase.GetCharactersParams("", PagingConfig(20)))

            verify(repository).getCharacters("")

            assertNotNull(result.first())
        }
}