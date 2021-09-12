package com.jks.userrecognitionandaccesspermission.repository

import com.jks.userrecognitionandaccesspermission.api.AdminApi
import com.jks.userrecognitionandaccesspermission.models.AllowSharingResponce
import com.jks.userrecognitionandaccesspermission.models.MyProfileResponce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AdminMainRepository @Inject constructor(private val adminApi: AdminApi) {

     fun allowAccess(userId:String,token:String):Flow<AllowSharingResponce> = flow {
         val responce= adminApi.allowAccess(userId, token)
         emit(responce)
     }.flowOn(Dispatchers.IO)

    fun removeAccess(userId:String,token:String):Flow<AllowSharingResponce> = flow {
        val responce= adminApi.removeAccess(userId, token)
        emit(responce)
    }.flowOn(Dispatchers.IO)

    fun myprofile(token:String):Flow<MyProfileResponce> = flow {
        val responce = adminApi.myprofile(token)
        emit(responce)
    }.flowOn(Dispatchers.IO)

}