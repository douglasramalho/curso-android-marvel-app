package com.example.marvelapp.framework.di

import com.example.core.usecase.base.AppCoroutinesDispatchers
import com.example.core.usecase.base.CoroutinesDispatchers
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
interface CoroutinesModule {

    @Binds
    fun bindDispatchers(dispatchers: AppCoroutinesDispatchers): CoroutinesDispatchers
}