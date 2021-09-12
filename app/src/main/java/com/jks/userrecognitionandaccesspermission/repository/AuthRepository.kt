package com.jks.userrecognitionandaccesspermission.repository

import com.jks.userrecognitionandaccesspermission.api.AuthApiService
import com.jks.userrecognitionandaccesspermission.models.SignInResponce
import com.jks.userrecognitionandaccesspermission.models.SignupResponce
import com.jks.userrecognitionandaccesspermission.models.User
import com.jks.userrecognitionandaccesspermission.models.UserLoginModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepository @Inject constructor(val authApiService: AuthApiService) {


    fun register(user: User):Flow<SignupResponce> = flow {
        val responce = authApiService.register(user)
        emit(responce)
    }.flowOn(Dispatchers.IO)

    fun signin(userLoginModel: UserLoginModel):Flow<SignInResponce> = flow {
        val responce = authApiService.signin(userLoginModel)
        emit(responce)
    }.flowOn(Dispatchers.IO)




}