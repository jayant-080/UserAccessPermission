package com.jks.userrecognitionandaccesspermission.models

import com.squareup.moshi.Json

data class SignInResponce(@Json(name = "success")
                          val success: Boolean = false,
                          @Json(name = "message")
                          val message: String = "",
                          @Json(name = "token")
                          val token: String = "")