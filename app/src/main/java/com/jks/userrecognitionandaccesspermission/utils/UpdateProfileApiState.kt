package com.jks.userrecognitionandaccesspermission.utils


import com.jks.userrecognitionandaccesspermission.models.UpdateProfilePicResponce

sealed class UpdateProfileApiState {

    object Loading:UpdateProfileApiState()
    class Failure(val mssg:Throwable):UpdateProfileApiState()
    class Success(val responce : UpdateProfilePicResponce):UpdateProfileApiState()
    object Empty:UpdateProfileApiState()
}