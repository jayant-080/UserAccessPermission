package com.jks.userrecognitionandaccesspermission.api

import com.jks.userrecognitionandaccesspermission.models.*
import retrofit2.http.*

interface UserApiService {

    companion object{
        const val USER_BASE_URL = "https://remoteuseraccesspermission.herokuapp.com"
    }

    @GET("/allusers")
    suspend fun getallUser(
        @Header("Authorization") token:String
    ):GetAllUserResponce

    @Headers("Content-Type:application/json; charset=UTF-8")
    @PUT("/request/{adminId}")
    suspend fun request(
        @Path("adminId") adminId:String,
        @Header("Authorization") token:String
    ):SentRequestResponce

    @Headers("Content-Type:application/json; charset=UTF-8")
    @GET("/feed/{adminId}")
    suspend fun getAllFeed(
        @Path("adminId") adminId: String,
        @Header("Authorization") token:String
    ):GetAllFeedResponce

    @Headers("Content-Type:application/json; charset=UTF-8")
    @GET("/adminfeed/{adminId}")
    suspend fun getAllFeedAdmin(
        @Path("adminId") adminId: String,
        @Header("Authorization") token:String
    ):GetAllFeedResponce

    @GET("/search/{query}")
    suspend fun searchPeople(
        @Path("query") query: String,
        @Header("Authorization") token:String
    ):SearchResponce
}