package com.jks.userrecognitionandaccesspermission.models


import com.squareup.moshi.Json

data class FeedItem(@Json(name = "postedBy")
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





data class GetAllFeedResponce(@Json(name = "feed")
                              val feed: List<FeedItem>?,
                              @Json(name = "success")
                              val success: Boolean = false,
                              @Json(name = "message")
                              val message: String = "")


