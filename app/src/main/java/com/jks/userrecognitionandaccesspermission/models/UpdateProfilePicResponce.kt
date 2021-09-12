package com.jks.userrecognitionandaccesspermission.models


import com.squareup.moshi.Json

data class UpdateProfilePicResponce(@Json(name = "result")
                                    val result: Result,
                                    @Json(name = "success")
                                    val success: Boolean = false,
                                    @Json(name = "message")
                                    val message: String = "")





