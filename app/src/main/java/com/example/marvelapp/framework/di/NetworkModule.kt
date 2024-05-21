package com.example.marvelapp.framework.di

import com.example.core.data.network.interceptor.AuthorizationInterceptor
import com.example.marvelapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar
import java.util.TimeZone
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  private const val TIMEOUT_SECONDS = 15L

  @Provides
  fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    setLevel(
      if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor.Level.BODY
      } else {
        HttpLoggingInterceptor.Level.NONE
      }
    )
  }

  @Provides
  fun provideAuthorizationInterceptor(): AuthorizationInterceptor = AuthorizationInterceptor(
    publicKey = BuildConfig.PUBLIC_KEY,
    privateKey = BuildConfig.PRIVATE_KEY,
    calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
  )

  @Provides
  fun provideOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor,
    authorizationInterceptor: AuthorizationInterceptor
  ): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .addInterceptor(authorizationInterceptor)
    .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
    .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
    .build()

  @Provides
  fun provideGsonConvertFactory(): GsonConverterFactory = GsonConverterFactory.create()

  @Provides
  fun provideRetrofit(
    okHttpClient: OkHttpClient,
    converterFactory: GsonConverterFactory
  ): Retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(converterFactory)
    .build()


}