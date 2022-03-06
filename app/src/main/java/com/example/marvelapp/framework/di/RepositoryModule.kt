package com.example.marvelapp.framework.di

import br.com.nicolas.core.data.repository.CharacterRemoteDataSource
import br.com.nicolas.core.data.repository.CharacterRepository
import com.example.marvelapp.framework.CharactersRepositoryImpl
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import com.example.marvelapp.framework.remote.RetrofitCharactersDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindCharactersRepository(repository: CharactersRepositoryImpl): CharacterRepository

    @Binds
    fun bindRemoteDataSource(
        dataSource: RetrofitCharactersDataSource
    ): CharacterRemoteDataSource<DataWrapperResponse>
}