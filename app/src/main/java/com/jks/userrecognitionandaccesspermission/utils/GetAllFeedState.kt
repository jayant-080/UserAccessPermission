package com.jks.userrecognitionandaccesspermission.utils

import com.jks.userrecognitionandaccesspermission.models.GetAllFeedResponce


sealed class GetAllFeedState {

    object Loading:GetAllFeedState()
    class Failure(val mssg:Throwable):GetAllFeedState()
    class Success(val responce : GetAllFeedResponce):GetAllFeedState()
    object Empty:GetAllFeedState()
}