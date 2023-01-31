package com.example.marvelapp.framework.paging

import androidx.paging.PagingSource
import com.example.marvelapp.factory.response.DataWrapperResponseFactory
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import com.example.testing.MainCoroutineRule
import com.example.testing.model.CharacterFactory
import com.github.coutinhonobre.core.data.repository.CharactersRemoteDataSource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)

class CharactersPagingSourceTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var remoteDataSource: CharactersRemoteDataSource<DataWrapperResponse>

    private lateinit var charactersPagingSource: CharactersPagingSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        charactersPagingSource = CharactersPagingSource(
            remoteDataSource = remoteDataSource,
            query = ""
        )
    }

    @Test
    fun `should return a success load result when load is called`() = runTest {
        // Arrange
        whenever(remoteDataSource.fetchCharacters(any())).thenReturn(
            DataWrapperResponseFactory.create()
        )

        // Act
        val result = charactersPagingSource.load(
            params = PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        // Assert
        val expected = listOf(
            CharacterFactory.create(hero = CharacterFactory.Hero.ThreeDMan),
            CharacterFactory.create(hero = CharacterFactory.Hero.ABom)
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
}
