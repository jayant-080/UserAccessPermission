package com.jks.userrecognitionandaccesspermission.adminmainui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jks.userrecognitionandaccesspermission.R
import com.jks.userrecognitionandaccesspermission.adapter.AdminNotificationAdapter
import com.jks.userrecognitionandaccesspermission.listener.allowaccessListener
import com.jks.userrecognitionandaccesspermission.listener.removeAccessListener
import com.jks.userrecognitionandaccesspermission.models.NotificationData
import com.jks.userrecognitionandaccesspermission.models.PushNotification
import com.jks.userrecognitionandaccesspermission.models.RequestItem
import com.jks.userrecognitionandaccesspermission.utils.AllowShareAndRemoveApiState
import com.jks.userrecognitionandaccesspermission.utils.MyProfileApiState
import com.jks.userrecognitionandaccesspermission.utils.NotificationApiState
import com.jks.userrecognitionandaccesspermission.utils.snackbar
import com.jks.userrecognitionandaccesspermission.viewmodels.AdminMainViewmodel
import com.jks.userrecognitionandaccesspermission.viewmodels.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_admin_notification.*
import kotlinx.android.synthetic.main.fragment_listof_admins.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AdminNotificationFragment : Fragment(R.layout.fragment_admin_notification) ,allowaccessListener,removeAccessListener{

    private val adminMainViewmodel:AdminMainViewmodel by viewModels()
    private val notificationViewModel: NotificationViewModel by viewModels()
    var token:String?=null
    var name:String?=null
    var newlist:List<RequestItem>?=null
    lateinit var notificationAdapter:AdminNotificationAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs: SharedPreferences = requireActivity().getSharedPreferences("logindetails",
            Context.MODE_PRIVATE
        )
         token = prefs.getString("token",null)!!
        name= prefs.getString("name",null)!!
         adminMainViewmodel.myProfile(token!!)
         subscribeToObserver(token!!)

    }

    private fun subscribeToObserver(token: String) {
      lifecycleScope.launchWhenStarted {
          adminMainViewmodel.myprofile.collect {

              when(it){
                  is MyProfileApiState.Loading->{
                       pb_admin_notification.isVisible=true
                  }
                  is MyProfileApiState.Failure->{
                      pb_admin_notification.isVisible=false
                      root_admin_noti.snackbar(it.mssg.toString())
                  }
                  is MyProfileApiState.Success->{
                      pb_admin_notification.isVisible=false
                      newlist= it.responce.user.request
                       notificationAdapter = AdminNotificationAdapter(requireActivity(),it.responce.user.request!!,this@AdminNotificationFragment,this@AdminNotificationFragment)
                      rv_admin_notification.adapter=notificationAdapter
                      notificationAdapter.notifyDataSetChanged()
                  }
              }
          }

      }
    }

    override fun allowAccessListener(position: Int, itemview: View) {
      adminMainViewmodel.allowAccess(newlist!![position].Id,token!!)
        lifecycleScope.launchWhenStarted {
            adminMainViewmodel.allowaccess.collect {
                when(it){
                    is AllowShareAndRemoveApiState.Loading->{
                         root_admin_noti.snackbar("accepting...")
                    }
                    is AllowShareAndRemoveApiState.Failure->{
                        root_admin_noti.snackbar(it.mssg.toString())
                    }
                    is AllowShareAndRemoveApiState.Success->{
                        root_admin_noti.snackbar(it.responce.message)
                        notificationAdapter.notifyDataSetChanged()
                        val n_data= NotificationData("$name accepted your request","tap to view  their feeds")
                        val notification = PushNotification(n_data,newlist!![position].n_token)
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
                       root_admin_noti.snackbar("notification sending")
                    }
                    is NotificationApiState.Failure->{
                        root_admin_noti.snackbar(it.mssg.toString())
                        Log.d("loda",it.mssg.toString())

                    }
                    is NotificationApiState.Success->{
                        Log.d("loda",it.responce.errorBody().toString())
                        root_admin_noti.snackbar("notification sent")
                        adminMainViewmodel.myProfile(token!!)

                    }
                }
            }
        }
    }


    override fun removeAccessListener(position: Int, itemview: View) {
        adminMainViewmodel.removeAccess(newlist!![position].Id,token!!)
        lifecycleScope.launchWhenStarted {
            adminMainViewmodel.removeaccess.collect {
                when(it){
                    is AllowShareAndRemoveApiState.Loading->{
                        root_admin_noti.snackbar("removing...")
                    }
                    is AllowShareAndRemoveApiState.Failure->{
                        root_admin_noti.snackbar(it.mssg.toString())
                    }
                    is AllowShareAndRemoveApiState.Success->{
                        root_admin_noti.snackbar(it.responce.message)
                        notificationAdapter.notifyDataSetChanged()

                    }
                }
            }
        }
    }
}