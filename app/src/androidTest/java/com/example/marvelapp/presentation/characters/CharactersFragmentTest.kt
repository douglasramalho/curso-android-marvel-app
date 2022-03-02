package com.example.marvelapp.presentation.characters

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharactersFragmentTest {

    @Before
    fun setUp() {
        launchFragmentInContainer<CharactersFragment>()
    }

    @Test
    fun shouldShowCharacters_whenViewIsCreated(){

    }
}