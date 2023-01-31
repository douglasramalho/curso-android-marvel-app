package com.example.testing.model

import com.github.coutinhonobre.core.domain.model.Character

class CharacterFactory {
    fun create(hero: Hero) = when (hero) {
        Hero.ThreeDMan -> Character(
            name = "3-D Man",
            imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg"
        )
        Hero.ABom -> Character(
            name = "A-Bomb (HAS)",
            imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16.jpg"
        )
    }

    sealed interface Hero {
        object ThreeDMan : Hero
        object ABom : Hero
    }
}
