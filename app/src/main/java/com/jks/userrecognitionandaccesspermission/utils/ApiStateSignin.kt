package com.jks.userrecognitionandaccesspermission.utils

import com.jks.userrecognitionandaccesspermission.models.SignInResponce


sealed class ApiStateSignin {

    object Loading:ApiStateSignin()
    class Failure(val mssg:Throwable):ApiStateSignin()
    class Success(val responce :SignInResponce):ApiStateSignin()
    object Empty:ApiStateSignin()
}