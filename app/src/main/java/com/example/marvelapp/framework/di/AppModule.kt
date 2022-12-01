package com.example.marvelapp.framework.di

import com.example.marvelapp.framework.imageloader.GlideImageLoaderImpl
import com.example.marvelapp.framework.imageloader.ImageLoader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
interface AppModule {

    @Binds
    fun bindImageLoader(imageLoader: GlideImageLoaderImpl): ImageLoader
}