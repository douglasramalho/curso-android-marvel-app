package com.example.marvelapp.framework.di

import com.example.marvelapp.BuildConfig
import com.example.marvelapp.framework.di.qualifier.BaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BaseUrlModule {

    @Singleton
    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = BuildConfig.BASE_URL
}