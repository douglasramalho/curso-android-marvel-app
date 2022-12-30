package com.example.marvelapp.framework.di

import android.content.Context
import androidx.room.Room
import com.example.core.data.DbConstants.APP_DATABASE_NAME
import com.example.marvelapp.framework.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            APP_DATABASE_NAME
        ).build()

    @Provides
    fun providesFavoriteDao(appDatabase: AppDatabase) = appDatabase.favoriteDao()

    @Provides
    fun providesCharacterDao(appDatabase: AppDatabase) = appDatabase.characterDao()
}