package com.jks.userrecognitionandaccesspermission.utils

import com.jks.userrecognitionandaccesspermission.models.SearchResponce


sealed class SearchApiState {

    object Loading:SearchApiState()
    class Failure(val mssg:Throwable):SearchApiState()
    class Success(val responce : SearchResponce):SearchApiState()
    object Empty:SearchApiState()
}