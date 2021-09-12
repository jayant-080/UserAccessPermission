package com.jks.userrecognitionandaccesspermission.api

import com.jks.userrecognitionandaccesspermission.models.SignInResponce
import com.jks.userrecognitionandaccesspermission.models.SignupResponce
import com.jks.userrecognitionandaccesspermission.models.User
import com.jks.userrecognitionandaccesspermission.models.UserLoginModel
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    companion object{
        const val BASE_URL="https://remoteuseraccesspermission.herokuapp.com"
    }


    @POST("/register")
    suspend fun register(
       @Body user: User
    ): SignupResponce

    @POST("/signin")
    suspend fun signin(
        @Body userLoginModel: UserLoginModel
    ):SignInResponce




}