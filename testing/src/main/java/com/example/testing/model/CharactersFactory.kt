package com.example.testing.model

import com.example.core.domain.model.Character

class CharactersFactory {

    fun create(hero: Hero) = when (hero) {
        Hero.ThreeDMan -> Character(
            1011334,
            "3-D Man",
            "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg"
        )
        Hero.ABomb -> Character(
            1017100,
            "A-Bomb (HAS)",
            "http://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16.jpg"
        )
    }

    sealed class Hero {
        object ThreeDMan : Hero()
        object ABomb : Hero()
    }
}