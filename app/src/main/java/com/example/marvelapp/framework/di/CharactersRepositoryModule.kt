package com.example.marvelapp.framework.di

import com.example.core.data.repository.CharactersRemoteDataSource
import com.example.core.data.repository.CharactersRepository
import com.example.marvelapp.framework.CharactersRepositoryImpl
import com.example.marvelapp.framework.remote.RetrofitCharactersDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CharactersRepositoryModule {

    @Singleton
    @Binds
    fun bindCharacterRepository(repository: CharactersRepositoryImpl): CharactersRepository

    @Singleton
    @Binds
    fun bindRemoteDataSource(dataSource: RetrofitCharactersDataSource): CharactersRemoteDataSource
}