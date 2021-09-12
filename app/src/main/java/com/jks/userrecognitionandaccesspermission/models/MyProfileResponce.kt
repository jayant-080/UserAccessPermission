package com.jks.userrecognitionandaccesspermission.models


import com.squareup.moshi.Json





data class AUser(@Json(name = "request")
                val request: List<RequestItem>?,
                @Json(name = "createdAt")
                val createdAt: String = "",
                @Json(name = "password")
                val password: String = "",
                @Json(name = "isadmin")
                val isadmin: Boolean = false,
                @Json(name = "profilepic")
                val profilepic: String = "",
                @Json(name = "__v")
                val V: Int = 0,
                @Json(name = "name")
                val name: String = "",
                @Json(name = "_id")
                val Id: String = "",
                @Json(name = "allowsharing")
                val allowsharing: List<AllowsharingItem>?,
                @Json(name = "email")
                val email: String = "",
                @Json(name = "updatedAt")
                val updatedAt: String = "",
                 val n_token:String="")


data class MyProfileResponce(@Json(name = "success")
                             val success: Boolean = false,
                             @Json(name = "message")
                             val message: String = "",
                             @Json(name = "user")
                             val user: AUser)


