package com.jks.userrecognitionandaccesspermission.models

import com.squareup.moshi.Json

data class User(@Json(name = "name")
                val name: String = "",
                @Json(name = "email")
                val email: String = "",
                @Json(name = "password")
                val password: String = "",
                @Json(name = "isadmin")
                val isadmin: Boolean = false,
                 val n_token:String = "")