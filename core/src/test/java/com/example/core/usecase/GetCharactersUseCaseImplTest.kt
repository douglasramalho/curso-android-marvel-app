package com.example.core.usecase

import androidx.paging.PagingConfig
import com.example.core.data.repository.CharactersRepository
import com.example.core.usecase.GetCharactersUseCase.GetCharactersParams
import com.example.testing.MainCoroutinesRule
import com.example.testing.model.CharacterFactory
import com.example.testing.pagingsource.PagingSourceFactory
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetCharactersUseCaseImplTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutinesRule = MainCoroutinesRule()

    @Mock
    lateinit var repository: CharactersRepository

    private lateinit var getCharactersUseCase: GetCharactersUseCase

    private val hero = CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)

    private val fakePagingSource = PagingSourceFactory().create(listOf(hero))

    @Before
    fun setUp(){
        getCharactersUseCase = GetCharactersUseCaseImpl(repository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should validate flow paging data creating when invoke from use case is called`()
    = runBlockingTest {
        whenever(repository.getCharacters(""))
            .thenReturn(fakePagingSource)

        val result = getCharactersUseCase
            .invoke(GetCharactersParams("", PagingConfig(20)))

        verify(repository).getCharacters("")

        assertNotNull(result.first())
    }
}