package com.example.marvelapp.framework.di

import com.example.marvelapp.framework.CharactersRepositoryImpl
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import com.example.marvelapp.framework.remote.RetrofitCharactersDataSource
import com.github.coutinhonobre.core.data.repository.CharactersRemoteDataSource
import com.github.coutinhonobre.core.data.repository.CharactersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindCharacterRepository(repository: CharactersRepositoryImpl): CharactersRepository

    @Binds
    fun bindRemoteDataSource(
        data: RetrofitCharactersDataSource
    ): CharactersRemoteDataSource<DataWrapperResponse>
}
