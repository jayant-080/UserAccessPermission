package com.jks.userrecognitionandaccesspermission.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jks.userrecognitionandaccesspermission.models.User
import com.jks.userrecognitionandaccesspermission.models.UserLoginModel
import com.jks.userrecognitionandaccesspermission.repository.AuthRepository
import com.jks.userrecognitionandaccesspermission.utils.ApiState
import com.jks.userrecognitionandaccesspermission.utils.ApiStateSignin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(val authRepository: AuthRepository):ViewModel() {

    private val _authStateflow:MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    val authStateflow:StateFlow<ApiState> = _authStateflow

    private val _authloginStateflow:MutableStateFlow<ApiStateSignin> = MutableStateFlow(ApiStateSignin.Empty)
    val authloginStateflow:StateFlow<ApiStateSignin> = _authloginStateflow


    fun register(user: User) = viewModelScope.launch {
             _authStateflow.value=ApiState.Loading
             authRepository.register(user).catch { error->
                 _authStateflow.value=ApiState.Failure(error)
             }.collect {responce->
              _authStateflow.value=ApiState.Success(responce)
             }
    }

    fun signin(userLoginModel: UserLoginModel) = viewModelScope.launch {

        _authloginStateflow.value= ApiStateSignin.Loading
        authRepository.signin(userLoginModel).catch { error->
            _authloginStateflow.value=ApiStateSignin.Failure(error)
        }.collect {responce->
            _authloginStateflow.value=ApiStateSignin.Success(responce)
        }
    }






}