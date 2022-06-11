package br.example.lib.model

import br.com.dio.core.domain.model.Character

class CharacterFactory {

    fun create(hero: Hero) =
        when(hero){
            Hero.ThreeDMan ->  Character(
                "3-D Man",
                "https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg"
            )
            Hero.ABomb ->
            Character(
                "A-Bomb (HAS)",
                "https://i.annihil.us/u/prod/marvel/i/mg/c/e0/5232158de5d16.jpg"

            )

        // nao temos acesso a character pois o nosso modulo test
        // nao conhece o nosso modulo core dentro do domain model o nosso character

    }

    sealed class Hero{
        object ThreeDMan : Hero()
        object ABomb: Hero()

    }
}