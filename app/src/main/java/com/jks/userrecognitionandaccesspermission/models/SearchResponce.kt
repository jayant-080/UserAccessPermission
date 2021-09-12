package com.jks.userrecognitionandaccesspermission.models


import com.squareup.moshi.Json

data class SearchResponce(@Json(name = "success")
                          val success: Boolean = false,
                          @Json(name = "message")
                          val message: String = "",
                          @Json(name = "user")
                          val user: List<UserItemtwo>?)


data class UserItemtwo(@Json(name = "password")
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
                    val allowsharing: List<User>?,
                    @Json(name = "email")
                    val email: String = "",
                    @Json(name = "request")
                    val request:List<User>?,
                    val n_token:String=""
)