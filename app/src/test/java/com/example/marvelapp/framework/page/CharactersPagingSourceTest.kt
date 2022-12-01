package com.example.marvelapp.framework.page

import androidx.paging.PagingSource
import com.example.core.data.repository.CharactersRemoteDataSource
import com.example.core.domain.model.Character
import com.example.marvelapp.fractory.response.DataWrapperResponseFactory
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import com.example.testing.MainDispatcherRule
import com.example.testing.model.CharacterFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class CharactersPagingSourceTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var remoteDataSource: CharactersRemoteDataSource<DataWrapperResponse>

    private lateinit var charactersPagingSource: CharactersPagingSource

    private val dataWrapperResponseFactory = DataWrapperResponseFactory()

    private val characterFactory = CharacterFactory()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        charactersPagingSource = CharactersPagingSource(remoteDataSource, "")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return success load result when load is called`() = runTest {
        // Arrange
        whenever(remoteDataSource.fetchCharacters(any()))
            .thenReturn(dataWrapperResponseFactory.create())

        // Act
        val result = charactersPagingSource.load(
            PagingSource.LoadParams.Refresh(
                null,
                loadSize = 2,
                false
            )
        )

        //Assert
        val expected = listOf(
            characterFactory.create(CharacterFactory.Hero.ThreeDMan),
            characterFactory.create(CharacterFactory.Hero.ABomb),
        )

        assertEquals(
            PagingSource.LoadResult.Page(
                data = expected,
                prevKey = null,
                nextKey = 20
            ),
            result
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return error load result when load is called`() = runTest {

        //Arrange
        val exception = java.lang.RuntimeException()
        whenever(remoteDataSource.fetchCharacters(any()))
            .thenThrow(exception)

        //Act
        val result = charactersPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        //Assert
        assertEquals(
            PagingSource.LoadResult.Error<Int, Character>(exception),
            result
        )
    }

}