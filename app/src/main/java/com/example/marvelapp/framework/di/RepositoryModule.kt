package com.example.marvelapp.framework.di

import br.com.dio.core.data.repository.CharactersRemoteDataSource
import br.com.dio.core.data.repository.CharactersRepository
import com.example.marvelapp.framework.CharactersRepositoryImpl
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import com.example.marvelapp.framework.remote.RetrofitCharactersDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

//
@Module
@InstallIn(SingletonComponent::class)
// isso pq eu posso ter o meu remote data source do meu repository sendo utilizando em mais de um
// caso de uso, assim reaproveitamos as mesmas instancias do repositorio
// eu vou marcar ele como singletonComponent
interface RepositoryModule {

    // queremos injetar CharactersRepositoryImpl
    // quem tiver dependendo dessa interface aqui CharactersRepository eu quero injetar esse
    // CharactersRepositoryImpl
    // quem precisa do repository vai ta olhando pra abstracao ou seja pra interface
    // ou seja esse contrato de obter personagens do paging source
    // isso e muito legal pq se futuramente nos nao quisermos utilizar essa implementacao aqui
    // CharactersRepositoryImpl, quisermos utilizar uma nova implementacao que nao trabalha com paging
    // source por exemplo uma implementacao que trabalha com qualquer outra biblioteca
    // basta eu vir aqui no CharactersRepositoryImpl, implementar esse novo repository
    // apartir dessa interface CharactersRepository e a unica alteracao que vamos precisar fazer eh
    // trocar aqui CharactersRepositoryImpl pra nova implementacao
    // ao inves de termos que alterara a implementacao aqui CharactersRepositoryImpl que ja temos
    // pronta, simplesmente implementamos uma classe nova, que implementa essa interface
    // e aqui na lib fazemos a substituicao e o hilt vai cuidar pra nos


    @Binds
    fun bindCharacterRepository(repository: CharactersRepositoryImpl): CharactersRepository


    // vamos ensinar como ele vai injetar o remote data source
    @Binds
    fun bindRemoteDataSource(
        dataSource: RetrofitCharactersDataSource
    ): CharactersRemoteDataSource<DataWrapperResponse>

    // estou ensinando ao dagger hilt pra quando alguem depender desse cara aqui
    // CharactersRemoteDataSource<DataWrapperResponse>
    // eu nesse momento quero injetar um retrofitCharacterDataSource
    // o benefico e se o retrofit for descontinuado no futuro, eu simplemente implemento essa
    // interface aqui CharactersRemoteDataSource utilizando o ktor
    //  no lugar do retrofit RetrofitCharactersDataSource vai ser KtorCharactersDataSource
    // e assim meu codigo vai funcionar corretamente pq quem ta dependendo de um remote data source
    // vai ta dependendo da minha interface nao da minha implementacao

}