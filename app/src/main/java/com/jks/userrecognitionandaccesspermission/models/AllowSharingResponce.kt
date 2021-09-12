package com.jks.userrecognitionandaccesspermission.models


import com.squareup.moshi.Json

data class AllowsharingItem(@Json(name = "isadmin")
                            val isadmin: Boolean = false,
                            @Json(name = "profilepic")
                            val profilepic: String = "",
                            @Json(name = "name")
                            val name: String = "",
                            @Json(name = "_id")
                            val Id: String = "",
                            @Json(name = "email")
                            val email: String = "",
                            val n_token:String="")


data class AllowSharingResponce(@Json(name = "result")
                                val result: Result,
                                @Json(name = "success")
                                val success: Boolean = false,
                                @Json(name = "message")
                                val message: String = "")




