package com.jks.userrecognitionandaccesspermission.api

import com.jks.userrecognitionandaccesspermission.models.UploadFeedResponce
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface UploadfeedApi {




    @Multipart
    @POST("/feed")
    fun postFeed(
        @Part("title") title:RequestBody,
        @Part("description") description:RequestBody,
        @Part picture:MultipartBody.Part,
        @Header("Authorization") token:String
    ):Call<UploadFeedResponce>

    companion object {
        val moshi= Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val interceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)

        }
        val client = OkHttpClient.Builder().addInterceptor(interceptor).readTimeout(920, TimeUnit.SECONDS).connectTimeout(920,
            TimeUnit.SECONDS).build()

        operator fun invoke(): UploadfeedApi {
            return Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl("https://remoteuseraccesspermission.herokuapp.com")
                .client(client)
                .build()
                .create(UploadfeedApi::class.java)
        }
    }


}