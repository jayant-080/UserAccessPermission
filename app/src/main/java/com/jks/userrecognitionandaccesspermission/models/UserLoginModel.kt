package com.jks.userrecognitionandaccesspermission.models

import com.squareup.moshi.Json

data class UserLoginModel(
                @Json(name = "email")
                val email: String = "",
                @Json(name = "password")
                val password: String = "")