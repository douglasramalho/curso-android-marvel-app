package com.example.usecase

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.example.data.repository.CharactersRepository
import com.example.testing.MainCoroutineRule
import com.example.testing.model.CharacterFactory
import com.example.testing.pagingsource.PagingSourceFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetCharactersUseCaseImplTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainCoroutinesRule = MainCoroutineRule()

    @Mock
    lateinit var repository: CharactersRepository

    private lateinit var  getCharactersUseCase: GetCharactersUseCase

    private val hero = CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)

    private val fakePagingSource = PagingSourceFactory().create(listOf(hero))

    @Before
    fun setUp(){
        getCharactersUseCase = GetCharactersUseCaseImpl(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should validate flow paging data creatin when invoke from use case is called`() = runTest {
        whenever(repository.getCharacters(any()))
            .thenReturn(fakePagingSource)

        val result = getCharactersUseCase
            .invoke(GetCharactersUseCase.GetCharactersParams("", PagingConfig(20)))

        verify(repository).getCharacters(any())
        assertNotNull(result.first())
    }
}