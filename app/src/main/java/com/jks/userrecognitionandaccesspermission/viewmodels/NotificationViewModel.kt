package com.jks.userrecognitionandaccesspermission.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jks.userrecognitionandaccesspermission.models.PushNotification
import com.jks.userrecognitionandaccesspermission.repository.NotificationRepository
import com.jks.userrecognitionandaccesspermission.utils.NotificationApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(val notificationRepository: NotificationRepository):ViewModel() {

    private val _notificationstate:MutableStateFlow<NotificationApiState> = MutableStateFlow(NotificationApiState.Empty)
    val notificationstate:StateFlow<NotificationApiState> = _notificationstate

    fun postNotification(notification:PushNotification)= viewModelScope.launch {

        _notificationstate.value = NotificationApiState.Loading
        notificationRepository.postNotifiction(notification).catch { error->
            _notificationstate.value= NotificationApiState.Failure(error)
        }.collect {
            _notificationstate.value=NotificationApiState.Success(it)
        }
    }


}