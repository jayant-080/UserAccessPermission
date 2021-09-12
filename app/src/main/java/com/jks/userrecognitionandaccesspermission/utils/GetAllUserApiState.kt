package com.jks.userrecognitionandaccesspermission.utils

import com.jks.userrecognitionandaccesspermission.models.GetAllUserResponce


sealed class GetAllUserApiState {

    object Loading:GetAllUserApiState()
    class Failure(val mssg:Throwable):GetAllUserApiState()
    class Success(val responce : GetAllUserResponce):GetAllUserApiState()
    object Empty:GetAllUserApiState()
}