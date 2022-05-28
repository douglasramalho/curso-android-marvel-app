package com.example.marvelapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import com.example.core.usecase.base.CoroutinesDispatchers

@OptIn(ExperimentalCoroutinesApi::class)
class AppCoroutinesTestDispatcher(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())
) : CoroutinesDispatchers {

    val testDispatcherProvider = object : CoroutinesDispatchers {
        override fun default(): CoroutineDispatcher = testDispatcher
        override fun io(): CoroutineDispatcher = testDispatcher
        override fun main(): CoroutineDispatcher = testDispatcher
        override fun unconfined(): CoroutineDispatcher = testDispatcher
    }
}


@Module
@InstallIn(SingletonComponent::class)
object CoroutinesTestModule {

    @Provides
    fun providesTestDispatcher(): CoroutinesDispatchers = AppCoroutinesTestDispatcher()
}