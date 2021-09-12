package com.jks.userrecognitionandaccesspermission.utils

import com.jks.userrecognitionandaccesspermission.models.SignupResponce
import okhttp3.ResponseBody
import retrofit2.Response

sealed class NotificationApiState {

    object Loading:NotificationApiState()
    class Failure(val mssg:Throwable):NotificationApiState()
    class Success(val responce : Response<ResponseBody>):NotificationApiState()
    object Empty:NotificationApiState()
}