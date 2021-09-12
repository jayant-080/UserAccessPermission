package com.jks.userrecognitionandaccesspermission.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jks.userrecognitionandaccesspermission.models.SearchModel
import com.jks.userrecognitionandaccesspermission.repository.UserMainRepository
import com.jks.userrecognitionandaccesspermission.utils.GetAllFeedState
import com.jks.userrecognitionandaccesspermission.utils.GetAllUserApiState
import com.jks.userrecognitionandaccesspermission.utils.SearchApiState
import com.jks.userrecognitionandaccesspermission.utils.SentRequestApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserMainViewmodel @Inject constructor(private  val userMainRepository: UserMainRepository):ViewModel() {


    private val _getalluserstate : MutableStateFlow<GetAllUserApiState> = MutableStateFlow(GetAllUserApiState.Empty)
    val getalluserstate:StateFlow<GetAllUserApiState> = _getalluserstate

    private val _sentrequeststate : MutableStateFlow<SentRequestApiState> = MutableStateFlow(SentRequestApiState.Empty)
    val sentrequeststate:StateFlow<SentRequestApiState> = _sentrequeststate

    private val _getallfeed : MutableStateFlow<GetAllFeedState> = MutableStateFlow(GetAllFeedState.Empty)
    val getallfeed:StateFlow<GetAllFeedState> = _getallfeed


    private val _searchstate: MutableStateFlow<SearchApiState> = MutableStateFlow(SearchApiState.Empty)
    val searchstate:StateFlow<SearchApiState> = _searchstate

    fun getAllUser(token:String) = viewModelScope.launch {

        _getalluserstate.value = GetAllUserApiState.Loading

        userMainRepository.getallUser(token).catch { error->
             _getalluserstate.value = GetAllUserApiState.Failure(error)
        }.collect {
            _getalluserstate.value = GetAllUserApiState.Success(it)
        }
    }

    fun request(adminId:String,token:String) = viewModelScope.launch {

        _sentrequeststate.value = SentRequestApiState.Loading
        userMainRepository.request(adminId, token).catch { error->
             _sentrequeststate.value=SentRequestApiState.Failure(error)
        }.collect {
            _sentrequeststate.value=SentRequestApiState.Success(it)
        }
    }

    fun getAllFeed(adminId: String,token: String)=viewModelScope.launch {

        _getallfeed.value = GetAllFeedState.Loading
        userMainRepository.getAllFeed(adminId, token).catch { error->
            _getallfeed.value=GetAllFeedState.Failure(error)
        }.collect {
            _getallfeed.value=GetAllFeedState.Success(it)
        }
    }

    fun getAllFeedAdmin(adminId: String,token: String)=viewModelScope.launch {

        _getallfeed.value = GetAllFeedState.Loading
        userMainRepository.getAllFeedAdmin(adminId, token).catch { error->
            _getallfeed.value=GetAllFeedState.Failure(error)
        }.collect {
            _getallfeed.value=GetAllFeedState.Success(it)
        }
    }

    fun search(searchModel: String,token: String)=viewModelScope.launch {

        _searchstate.value = SearchApiState.Loading
        userMainRepository.search(searchModel, token).catch { error->
            _searchstate.value=SearchApiState.Failure(error)
        }.collect {
            _searchstate.value=SearchApiState.Success(it)
        }
    }

}