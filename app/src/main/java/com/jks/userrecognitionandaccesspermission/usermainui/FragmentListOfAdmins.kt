package com.jks.userrecognitionandaccesspermission.usermainui

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.jks.userrecognitionandaccesspermission.R
import com.jks.userrecognitionandaccesspermission.adapter.ListOfAdminAdapter
import com.jks.userrecognitionandaccesspermission.listener.SentRequestListener
import com.jks.userrecognitionandaccesspermission.listener.seefeedlistener
import com.jks.userrecognitionandaccesspermission.models.NotificationData
import com.jks.userrecognitionandaccesspermission.models.PushNotification
import com.jks.userrecognitionandaccesspermission.models.UserItem
import com.jks.userrecognitionandaccesspermission.utils.GetAllUserApiState
import com.jks.userrecognitionandaccesspermission.utils.NotificationApiState
import com.jks.userrecognitionandaccesspermission.utils.SentRequestApiState
import com.jks.userrecognitionandaccesspermission.utils.snackbar
import com.jks.userrecognitionandaccesspermission.viewmodels.NotificationViewModel
import com.jks.userrecognitionandaccesspermission.viewmodels.UserMainViewmodel
import com.squareup.moshi.Moshi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.each_row_alladmin_list.*
import kotlinx.android.synthetic.main.each_row_alladmin_list.view.*
import kotlinx.android.synthetic.main.fragment_listof_admins.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class FragmentListOfAdmins:Fragment(R.layout.fragment_listof_admins),SentRequestListener,seefeedlistener {

    private val userMainViewmodel:UserMainViewmodel by viewModels()
    private val notificationViewModel:NotificationViewModel by viewModels()
    private  var listofadmin:List<UserItem> = ArrayList()
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
        userMainViewmodel.getAllUser(token!!)
        subscribeToObserver()


        fab_search.setOnClickListener {
            val action = FragmentListOfAdminsDirections.actionFragmentListOfAdminsToSearchFragment()
            findNavController().navigate(action)
        }

        iv_setting.setOnClickListener {
            findNavController().navigate(
                FragmentListOfAdminsDirections.actionFragmentListOfAdminsToUpdateProfilePic()
            )
        }

    }

    private fun subscribeToObserver() {

        lifecycleScope.launchWhenStarted {
            userMainViewmodel.getalluserstate.collect {
              when(it){

                  is GetAllUserApiState.Loading->{
                      pb_listofadmin.isVisible=true
                  }
                  is GetAllUserApiState.Failure->{
                      pb_listofadmin.isVisible=false
                      root_listofadmin.snackbar(it.mssg.toString())
                  }
                  is GetAllUserApiState.Success->{
                      pb_listofadmin.isVisible=false
                      listofadmin=it.responce.user!!
                      val admintlistadapter = ListOfAdminAdapter(requireActivity(),it.responce.user!!,this@FragmentListOfAdmins,currentUid!!,this@FragmentListOfAdmins)
                      rv_listofadmin.adapter=admintlistadapter
                      admintlistadapter.notifyDataSetChanged()
                      Log.d("loda",currentUid!!)
                  }
              }
            }
        }

    }

    override fun sendRequestListener(position: Int,itemview:View) {
        userMainViewmodel.request(listofadmin[position].Id,token!!)

        Log.d("bsdka","you click to "+ listofadmin[position].name)
         lifecycleScope.launchWhenStarted {
             userMainViewmodel.sentrequeststate.collect {
               when(it){

                   is SentRequestApiState.Loading->{
                       root_listofadmin.snackbar("sending")
                   }
                   is SentRequestApiState.Failure->{
                       root_listofadmin.snackbar(it.mssg.toString())
                   }
                   is SentRequestApiState.Success->{
                       root_listofadmin.snackbar(it.responce.message)
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
                        root_listofadmin.snackbar("notification sending")
                    }
                    is NotificationApiState.Failure->{
                        root_listofadmin.snackbar(it.mssg.toString())
                        Log.d("loda",it.mssg.toString())

                    }
                    is NotificationApiState.Success->{
                        Log.d("loda",it.responce.errorBody().toString())
                        root_listofadmin.snackbar("notification sent")

                    }
                }
            }
        }
    }

    override fun seeFeedListener(position: Int, itemview: View) {
      val action = FragmentListOfAdminsDirections.actionFragmentListOfAdminsToGetFeeds(listofadmin[position].Id)
        findNavController().navigate(action)
    }
}