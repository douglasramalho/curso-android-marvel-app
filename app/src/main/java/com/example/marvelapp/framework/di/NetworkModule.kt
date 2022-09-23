package com.example.marvelapp.framework.di

import com.example.marvelapp.BuildConfig
import com.example.marvelapp.framework.network.interceptor.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val READ_TIMEOUT = 15L
    private const val CONNECT_TIMEOUT = 15L

    @Provides
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            setLevel(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            )
        }

    @Provides
    fun provideAuthorizationInterceptor() = AuthorizationInterceptor(
        publicKey = BuildConfig.PUBLIC_KEY,
        privateKey = BuildConfig.PRIVATE_KEY,
        calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    )

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authorizationInterceptor: AuthorizationInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authorizationInterceptor)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .build()

    @Provides
    fun provideGsonConverterFactory() = GsonConverterFactory.create()

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ) = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(converterFactory)
        .build()
}
