package com.example.marvelapp.framework.di

import com.example.marvelapp.commons.data.network.response.DataWrapperResponse
import com.example.marvelapp.features.heroes.data.network.datasource.CharactersRemoteDataSource
import com.example.marvelapp.features.heroes.data.network.datasource.CharactersRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    fun bindRemoteDataSource(
        dataSource: CharactersRemoteDataSourceImpl
    ): CharactersRemoteDataSource<DataWrapperResponse>
}