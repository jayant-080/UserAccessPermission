package com.jks.userrecognitionandaccesspermission.usermainui

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.jks.userrecognitionandaccesspermission.R
import com.jks.userrecognitionandaccesspermission.adapter.ListOfAdminAdapter
import com.jks.userrecognitionandaccesspermission.adapter.SearchAdapter
import com.jks.userrecognitionandaccesspermission.listener.SentRequestListener
import com.jks.userrecognitionandaccesspermission.listener.seefeedlistener
import com.jks.userrecognitionandaccesspermission.models.*
import com.jks.userrecognitionandaccesspermission.utils.*
import com.jks.userrecognitionandaccesspermission.viewmodels.NotificationViewModel
import com.jks.userrecognitionandaccesspermission.viewmodels.UserMainViewmodel
import com.squareup.moshi.Moshi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.each_row_alladmin_list.*
import kotlinx.android.synthetic.main.each_row_alladmin_list.view.*
import kotlinx.android.synthetic.main.fragment_listof_admins.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class SearchFragment:Fragment(R.layout.fragment_search),SentRequestListener,seefeedlistener {


    private val userMainViewmodel:UserMainViewmodel by viewModels()
    private val notificationViewModel:NotificationViewModel by viewModels()
    private  var listofadmin:List<UserItemtwo> = ArrayList()
    private var token:String?=null
    private var name:String?=null
    private var currentUid:String?=null
    private val topic="/topics/mytopic"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       FirebaseMessaging.getInstance().subscribeToTopic(topic)
        val prefs: SharedPreferences = requireActivity().getSharedPreferences("logindetails",
            Context.MODE_PRIVATE
        )
        token = prefs.getString("token",null)!!
        name = prefs.getString("name",null)!!
        currentUid=prefs.getString("id",null)
        subscribeToObserver()
        var job: Job?=null
        et_search.addTextChangedListener {
            job?.cancel()
            job= lifecycleScope.launch {
                delay(1000L)
                it?.let {qm->
                    userMainViewmodel.search(et_search.text.toString() ,token!!)
                }
            }

        }


    }

    private fun subscribeToObserver() {

        lifecycleScope.launchWhenStarted {
            userMainViewmodel.searchstate.collect {
              when(it){

                  is SearchApiState.Loading->{
                      pb_search.isVisible=true
                  }
                  is SearchApiState.Failure->{
                      pb_search.isVisible=false
                      root_search.snackbar(it.mssg.toString())
                      Log.d("loda",it.mssg.toString())
                  }
                  is SearchApiState.Success->{
                      pb_search.isVisible=false
                      listofadmin=it.responce.user!!
                      val admintlistadapter = SearchAdapter(requireActivity(),it.responce.user!!,this@SearchFragment,currentUid!!,this@SearchFragment)
                      rv_search.adapter=admintlistadapter
                      admintlistadapter.notifyDataSetChanged()

                  }
              }
            }
        }

    }

    override fun sendRequestListener(position: Int,itemview:View) {
        userMainViewmodel.request(listofadmin[position].Id,token!!)

         lifecycleScope.launchWhenStarted {
             userMainViewmodel.sentrequeststate.collect {
               when(it){

                   is SentRequestApiState.Loading->{
                       root_search.snackbar("sending")
                   }
                   is SentRequestApiState.Failure->{
                       root_search.snackbar(it.mssg.toString())
                   }
                   is SentRequestApiState.Success->{
                       root_search.snackbar(it.responce.message)
                       itemview.button_send_request.isVisible=false
                       itemview.button_send_request_green.isVisible=true
                       val n_data= NotificationData("$name sends you a request","tap to view ")
                       val notification = PushNotification(n_data,listofadmin[position].n_token)
                       notificationViewModel.postNotification(notification)
                       subscribeToNotification()
                   }
               }
             }
         }
    }

    private fun subscribeToNotification() {
        lifecycleScope.launchWhenStarted {
            notificationViewModel.notificationstate.collect {
                when(it){
                    is NotificationApiState.Loading->{
                        root_search.snackbar("notification sending")
                    }
                    is NotificationApiState.Failure->{
                        root_search.snackbar(it.mssg.toString())
                        Log.d("loda",it.mssg.toString())

                    }
                    is NotificationApiState.Success->{
                        Log.d("loda",it.responce.errorBody().toString())
                        root_search.snackbar("notification sent")

                    }
                }
            }
        }
    }

    override fun seeFeedListener(position: Int, itemview: View) {
        val action = SearchFragmentDirections.actionSearchFragmentToGetFeeds(listofadmin[position].Id)
        findNavController().navigate(action)
    }
}