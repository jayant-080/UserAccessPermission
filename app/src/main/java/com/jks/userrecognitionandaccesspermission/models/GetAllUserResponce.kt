package com.jks.userrecognitionandaccesspermission.models


import com.squareup.moshi.Json

data class GetAllUserResponce(@Json(name = "success")
                              val success: Boolean = false,
                              @Json(name = "message")
                              val message: String = "",
                              @Json(name = "user")
                              val user: List<UserItem>?)


data class UserItem(@Json(name = "password")
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
                    val allowsharing: List<String>?,
                    @Json(name = "email")
                    val email: String = "",
                    @Json(name = "request")
                    val request:List<String>?,
                    val n_token:String=""
                    )







