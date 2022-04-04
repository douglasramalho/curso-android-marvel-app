package br.com.dio.core.data.repository

interface CharactersRemoteDataSource<T> {

    // nos vimos que vamos passar nos parameter dessa forma
    suspend fun fetchCharacters(queries: Map<String,String>): T
        // aqui estamos chamado a funcao assim pois se parece com api
        // aqui tambem ja estamos na fonte de dados remota estamos mais perto da nossa api
        // DataWrapperResponse isso que o nosso endpoint de personagens retorna
        // como nos movemos esse objeto pra dentro do modulo do app
        // o nosso core claro nao tem acesso a esse carinha aqui
        // pra resolver isso colocamos pra nossa interface receber um tipo generico
        // ou seja vai precisar passar qual que eh o tipo que vc quer trabalhar
        // qual e o tipo de retorno que vc quer trabalhar

}