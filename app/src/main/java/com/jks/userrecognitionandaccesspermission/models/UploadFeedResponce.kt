package com.jks.userrecognitionandaccesspermission.models


import com.squareup.moshi.Json

data class PostedBy(@Json(name = "password")
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
                    val n_token:String=""
                    )


data class UploadFeedResponce(@Json(name = "feed")
                              val feed: Feed,
                              @Json(name = "success")
                              val success: Boolean = false,
                              @Json(name = "message")
                              val message: String = "")


data class Feed(@Json(name = "postedBy")
                val postedBy: PostedBy,
                @Json(name = "createdAt")
                val createdAt: String = "",
                @Json(name = "__v")
                val V: Int = 0,
                @Json(name = "description")
                val description: String = "",
                @Json(name = "_id")
                val Id: String = "",
                @Json(name = "title")
                val title: String = "",
                @Json(name = "picture")
                val picture: String = "",
                @Json(name = "updatedAt")
                val updatedAt: String = "")


