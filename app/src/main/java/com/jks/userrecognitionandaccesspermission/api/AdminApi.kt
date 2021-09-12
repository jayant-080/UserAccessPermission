package com.jks.userrecognitionandaccesspermission.api

import com.jks.userrecognitionandaccesspermission.models.AllowSharingResponce
import com.jks.userrecognitionandaccesspermission.models.MyProfileResponce
import retrofit2.http.*

interface AdminApi {


    @Headers("Content-Type:application/json; charset=UTF-8")
    @PUT("/allowsharing/{userId}")
    suspend fun allowAccess(
        @Path("userId") userId:String,
        @Header("Authorization") token:String
    ):AllowSharingResponce

    @Headers("Content-Type:application/json; charset=UTF-8")
    @PUT("/stopsharing/{userId}")
    suspend fun removeAccess(
        @Path("userId") userId:String,
        @Header("Authorization") token:String
    ):AllowSharingResponce

    @GET("/myprofile")
    suspend fun myprofile(
        @Header("Authorization") token:String
    ):MyProfileResponce
}