package com.jks.userrecognitionandaccesspermission.utils

import com.jks.userrecognitionandaccesspermission.models.MyProfileResponce


sealed class MyProfileApiState {

    object Loading:MyProfileApiState()
    class Failure(val mssg:Throwable):MyProfileApiState()
    class Success(val responce : MyProfileResponce):MyProfileApiState()
    object Empty:MyProfileApiState()
}