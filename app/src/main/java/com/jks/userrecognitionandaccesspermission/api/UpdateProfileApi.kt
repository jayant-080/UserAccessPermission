package com.jks.userrecognitionandaccesspermission.api


import com.jks.userrecognitionandaccesspermission.models.UpdateProfilePicResponce
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface UpdateProfileApi {

    @Multipart
    @PUT("/updateprofilepic/{userId}")
    suspend fun updatepic(
        @Path("userId") userId:String,
        @Part picture:MultipartBody.Part,
        @Header("Authorization") token:String
    ):UpdateProfilePicResponce

}