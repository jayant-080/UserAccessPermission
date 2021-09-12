package com.jks.userrecognitionandaccesspermission.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jks.userrecognitionandaccesspermission.repository.UpdateProfilePicRepository
import com.jks.userrecognitionandaccesspermission.utils.UpdateProfileApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class UpdateProfilePicViewModel @Inject constructor(val updateProfilePicRepository: UpdateProfilePicRepository):ViewModel(){


    private val _updatepicstate:MutableStateFlow<UpdateProfileApiState> = MutableStateFlow(UpdateProfileApiState.Empty)
    val updateprofilestate:StateFlow<UpdateProfileApiState> = _updatepicstate

    fun updatePic(userid:String,image:MultipartBody.Part,token:String)= viewModelScope.launch {

        _updatepicstate.value = UpdateProfileApiState.Loading
      updateProfilePicRepository.updatePic(userid,image,token).catch {error->
           _updatepicstate.value=UpdateProfileApiState.Failure(error)
      }.collect {
          _updatepicstate.value=UpdateProfileApiState.Success(it)
      }

    }


}