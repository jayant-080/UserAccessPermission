package com.jks.userrecognitionandaccesspermission.repository

import com.jks.userrecognitionandaccesspermission.api.UserApiService
import com.jks.userrecognitionandaccesspermission.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserMainRepository @Inject constructor(private val userApiService: UserApiService) {


   fun getallUser(token:String):Flow<GetAllUserResponce> = flow {
       val responce = userApiService.getallUser(token)
       emit(responce)
   }.flowOn(Dispatchers.IO)

    fun request(adminId:String,token:String):Flow<SentRequestResponce> = flow {
        val responce = userApiService.request(adminId, token)
        emit(responce)
    }.flowOn(Dispatchers.IO)

    fun getAllFeed(adminId: String,token: String):Flow<GetAllFeedResponce> = flow {
        val responce = userApiService.getAllFeed(adminId, token)
        emit(responce)
    }.flowOn(Dispatchers.IO)

    fun getAllFeedAdmin(adminId: String,token: String):Flow<GetAllFeedResponce> = flow {
        val responce = userApiService.getAllFeedAdmin(adminId, token)
        emit(responce)
    }.flowOn(Dispatchers.IO)

    fun search(searchModel: String,token: String):Flow<SearchResponce> = flow {
        val responce = userApiService.searchPeople(searchModel,token)
        emit(responce)

    }.flowOn(Dispatchers.IO)

}