package com.jks.userrecognitionandaccesspermission.di

import com.jks.userrecognitionandaccesspermission.api.*
import com.jks.userrecognitionandaccesspermission.api.AuthApiService.Companion.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    @Singleton
    @Provides
    fun getUnsafeOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)
            .connectTimeout(920, TimeUnit.SECONDS)
            .readTimeout(920, TimeUnit.SECONDS)
            .followRedirects(true)
            .followSslRedirects(true)
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Content-Type","application/json")
                    .build()
                chain.proceed(newRequest)
            }

        return builder.build()

    }


    @Singleton
    @Provides
    fun provideMoshi() = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun provideAuthApiService(moshi: Moshi, okHttpClient: OkHttpClient) = Retrofit.
          Builder().
          addConverterFactory(MoshiConverterFactory.create(moshi)).
          client(okHttpClient).
          baseUrl(BASE_URL).
          build().
          create(AuthApiService::class.java)


    @Singleton
    @Provides
    fun provideUserApiService(moshi: Moshi, okHttpClient: OkHttpClient) = Retrofit.
    Builder().
    addConverterFactory(MoshiConverterFactory.create(moshi)).
    client(okHttpClient).
    baseUrl(UserApiService.USER_BASE_URL).
    build().
    create(UserApiService::class.java)

    @Singleton
    @Provides
    fun provideUserAdminAPi(moshi: Moshi, okHttpClient: OkHttpClient) = Retrofit.
    Builder().
    addConverterFactory(MoshiConverterFactory.create(moshi)).
    client(okHttpClient).
    baseUrl(UserApiService.USER_BASE_URL).
    build().
    create(AdminApi::class.java)

    @Singleton
    @Provides
    fun provideNotificationApi(moshi: Moshi, okHttpClient: OkHttpClient) = Retrofit.
    Builder().
    addConverterFactory(MoshiConverterFactory.create(moshi)).
    client(okHttpClient).
    baseUrl("https://fcm.googleapis.com").
    build().
    create(NotificationApi::class.java)

    @Singleton
    @Provides
    fun provideUpdateProfileApi(moshi: Moshi, okHttpClient: OkHttpClient) = Retrofit.
    Builder().
    addConverterFactory(MoshiConverterFactory.create(moshi)).
    client(okHttpClient).
    baseUrl("https://remoteuseraccesspermission.herokuapp.com").
    build().
    create(UpdateProfileApi::class.java)
}