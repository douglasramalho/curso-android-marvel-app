package com.example.marvelapp.framework.di

import android.content.Context
import androidx.room.Room
import com.example.core.data.DbConstants.APP_DATABASE_NAME
import com.example.marvelapp.framework.db.AppDatabase
import com.example.marvelapp.framework.db.dao.CharacterDao
import com.example.marvelapp.framework.db.dao.FavoriteDao
import com.example.marvelapp.framework.db.dao.RemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, APP_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideFavoriteDao(appDatabase: AppDatabase): FavoriteDao = appDatabase.favoriteDao()

    @Singleton
    @Provides
    fun provideCharacterDao(appDatabase: AppDatabase): CharacterDao = appDatabase.characterDao()

    @Singleton
    @Provides
    fun provideRemoteKeyDao(appDatabase: AppDatabase): RemoteKeyDao = appDatabase.remoteKeyDao()
}