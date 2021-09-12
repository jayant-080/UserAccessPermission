package com.jks.userrecognitionandaccesspermission.repository

import com.jks.userrecognitionandaccesspermission.api.NotificationApi
import com.jks.userrecognitionandaccesspermission.models.PushNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class NotificationRepository @Inject constructor(val notificationApi: NotificationApi) {


    fun postNotifiction(notification:PushNotification):Flow<Response<ResponseBody>> = flow {
        val responce = notificationApi.postNotification(notification)
        emit(responce)
    }.flowOn(Dispatchers.IO)
}