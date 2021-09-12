package com.jks.userrecognitionandaccesspermission.repository

import com.jks.userrecognitionandaccesspermission.api.UpdateProfileApi
import com.jks.userrecognitionandaccesspermission.models.UpdateProfilePicResponce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import retrofit2.Call
import javax.inject.Inject

class UpdateProfilePicRepository @Inject constructor(val updateProfileApi: UpdateProfileApi) {


    fun updatePic(userId:String,image:MultipartBody.Part,token:String):Flow<UpdateProfilePicResponce> = flow {
        val responce = updateProfileApi.updatepic(userId,image,token)
        emit(responce)
    }.flowOn(Dispatchers.IO)

}