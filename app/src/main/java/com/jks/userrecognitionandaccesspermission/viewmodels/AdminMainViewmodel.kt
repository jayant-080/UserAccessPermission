package com.jks.userrecognitionandaccesspermission.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jks.userrecognitionandaccesspermission.repository.AdminMainRepository
import com.jks.userrecognitionandaccesspermission.repository.UserMainRepository
import com.jks.userrecognitionandaccesspermission.utils.AllowShareAndRemoveApiState
import com.jks.userrecognitionandaccesspermission.utils.GetAllUserApiState
import com.jks.userrecognitionandaccesspermission.utils.MyProfileApiState
import com.jks.userrecognitionandaccesspermission.utils.SentRequestApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminMainViewmodel @Inject constructor(private  val adminMainRepository: AdminMainRepository):ViewModel() {


    private val _allowaccess : MutableStateFlow<AllowShareAndRemoveApiState> = MutableStateFlow(AllowShareAndRemoveApiState.Empty)
    val allowaccess:StateFlow<AllowShareAndRemoveApiState> = _allowaccess

    private val _removeaccess : MutableStateFlow<AllowShareAndRemoveApiState> = MutableStateFlow(AllowShareAndRemoveApiState.Empty)
    val removeaccess:StateFlow<AllowShareAndRemoveApiState> = _removeaccess

    private val _myprofile : MutableStateFlow<MyProfileApiState> = MutableStateFlow(MyProfileApiState.Empty)
    val myprofile:StateFlow<MyProfileApiState> = _myprofile


    fun allowAccess(userid:String,token:String)= viewModelScope.launch {

        _allowaccess.value=AllowShareAndRemoveApiState.Loading
        adminMainRepository.allowAccess(userid,token).catch { error->
            _allowaccess.value=AllowShareAndRemoveApiState.Failure(error)
        }.collect{
            _allowaccess.value=AllowShareAndRemoveApiState.Success(it)
        }
    }


    fun removeAccess(userid:String,token:String)= viewModelScope.launch {

        _removeaccess.value=AllowShareAndRemoveApiState.Loading
        adminMainRepository.removeAccess(userid,token).catch { error->
            _removeaccess.value=AllowShareAndRemoveApiState.Failure(error)
        }.collect{
            _removeaccess.value=AllowShareAndRemoveApiState.Success(it)
        }
    }

    fun myProfile(token:String) = viewModelScope.launch {
        _myprofile.value = MyProfileApiState.Loading
        adminMainRepository.myprofile(token).catch { error->
            _myprofile.value=MyProfileApiState.Failure(error)
        }.collect {
            _myprofile.value=MyProfileApiState.Success(it)
        }
    }

}