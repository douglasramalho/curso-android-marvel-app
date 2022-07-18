package com.example.marvelapp.framework.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.example.core.usecase.base.AppCoroutinesDispatchers
import com.example.core.usecase.base.CoroutinesDispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CoroutinesModule {

    @Singleton
    @Binds
    fun bindDispatcher(dispatchers: AppCoroutinesDispatchers): CoroutinesDispatchers
}