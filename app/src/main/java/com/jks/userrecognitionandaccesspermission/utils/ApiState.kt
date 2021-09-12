package com.jks.userrecognitionandaccesspermission.utils

import com.jks.userrecognitionandaccesspermission.models.SignupResponce

sealed class ApiState {

    object Loading:ApiState()
    class Failure(val mssg:Throwable):ApiState()
    class Success(val responce : SignupResponce):ApiState()
    object Empty:ApiState()
}