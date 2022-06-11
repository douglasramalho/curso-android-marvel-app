package com.example.marvelapp.framework.di

import br.com.dio.core.usecase.GetCharactersUseCase
import br.com.dio.core.usecase.GetCharactersUseCaseImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
// o caso de uso vai ter o ciclo de vida do meu viewModel
// quando o meu viewModel morrer, vamos destruir tambem o nosso caso de uso
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    // fazendo isso ele ja reconhece que tem alguem utilizando essa interface
    @Binds
    fun bindCharactersUseCase(useCase: GetCharactersUseCaseImp): GetCharactersUseCase
}