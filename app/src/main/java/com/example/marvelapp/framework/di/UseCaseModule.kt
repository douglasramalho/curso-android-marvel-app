package com.example.marvelapp.framework.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import usecase.AddFavoriteUseCase
import usecase.AddFavoriteUseCaseImpl
import usecase.CheckFavoriteUseCase
import usecase.CheckFavoriteUseCaseImpl
import usecase.GetCharacterCategoriesUseCase
import usecase.GetCharacterCategoriesUseCaseImpl
import usecase.GetCharactersUseCase
import usecase.GetCharactersUseCaseImpl
import usecase.RemoveFavoriteUseCase
import usecase.RemoveFavoriteUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindGetCharactersUseCase(useCase: GetCharactersUseCaseImpl): GetCharactersUseCase

    @Binds
    fun bindGetComicsUseCase(useCase: GetCharacterCategoriesUseCaseImpl): GetCharacterCategoriesUseCase

    @Binds
    fun bindCheckFavoriteUseCase(useCase: CheckFavoriteUseCaseImpl): CheckFavoriteUseCase

    @Binds
    fun bindAddFavoriteUseCase(useCase: AddFavoriteUseCaseImpl): AddFavoriteUseCase

    @Binds
    fun bindRemoveFavoriteUseCase(useCase: RemoveFavoriteUseCaseImpl): RemoveFavoriteUseCase
}