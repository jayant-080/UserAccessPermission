package com.jks.userrecognitionandaccesspermission.utils

import com.jks.userrecognitionandaccesspermission.models.AllowSharingResponce

sealed class AllowShareAndRemoveApiState {

    object Loading:AllowShareAndRemoveApiState()
    class Failure(val mssg:Throwable):AllowShareAndRemoveApiState()
    class Success(val responce : AllowSharingResponce):AllowShareAndRemoveApiState()
    object Empty:AllowShareAndRemoveApiState()
}