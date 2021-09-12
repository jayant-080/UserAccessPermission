package com.jks.userrecognitionandaccesspermission.utils

import com.jks.userrecognitionandaccesspermission.models.SentRequestResponce


sealed class SentRequestApiState {

    object Loading:SentRequestApiState()
    class Failure(val mssg:Throwable):SentRequestApiState()
    class Success(val responce : SentRequestResponce):SentRequestApiState()
    object Empty:SentRequestApiState()
}