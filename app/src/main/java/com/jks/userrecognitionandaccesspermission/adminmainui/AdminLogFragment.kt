package com.jks.userrecognitionandaccesspermission.adminmainui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jks.userrecognitionandaccesspermission.R
import com.jks.userrecognitionandaccesspermission.adapter.AdminLogAdapter
import com.jks.userrecognitionandaccesspermission.adapter.AdminNotificationAdapter
import com.jks.userrecognitionandaccesspermission.listener.allowaccessListener
import com.jks.userrecognitionandaccesspermission.listener.removeAccessListener
import com.jks.userrecognitionandaccesspermission.models.AllowsharingItem
import com.jks.userrecognitionandaccesspermission.utils.AllowShareAndRemoveApiState
import com.jks.userrecognitionandaccesspermission.utils.MyProfileApiState
import com.jks.userrecognitionandaccesspermission.utils.snackbar
import com.jks.userrecognitionandaccesspermission.viewmodels.AdminMainViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_admin_log.*
import kotlinx.android.synthetic.main.fragment_admin_notification.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AdminLogFragment : Fragment(R.layout.fragment_admin_log) , removeAccessListener {

    private val adminMainViewmodel: AdminMainViewmodel by viewModels()
    var token:String?=null
    var newlist:List<AllowsharingItem>?=null
    lateinit var adminlogAdapter: AdminLogAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs: SharedPreferences = requireActivity().getSharedPreferences("logindetails",
            Context.MODE_PRIVATE
        )
        token = prefs.getString("token",null)!!
        adminMainViewmodel.myProfile(token!!)
        subscribeToObserver(token!!)
    }

    private fun subscribeToObserver(token: String) {
        lifecycleScope.launchWhenStarted {
            adminMainViewmodel.myprofile.collect {

                when(it){
                    is MyProfileApiState.Loading->{
                        pb_admin_log.isVisible=true
                    }
                    is MyProfileApiState.Failure->{
                        pb_admin_log.isVisible=false
                        root_admin_log.snackbar(it.mssg.toString())
                    }
                    is MyProfileApiState.Success->{
                        pb_admin_log.isVisible=false
                        newlist= it.responce.user.allowsharing
                        adminlogAdapter = AdminLogAdapter(requireActivity(),it.responce.user.allowsharing!!,this@AdminLogFragment)
                        rv_admin_log.adapter=adminlogAdapter
                        adminlogAdapter.notifyDataSetChanged()
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
                        root_admin_log.snackbar("removing...")
                    }
                    is AllowShareAndRemoveApiState.Failure->{
                        root_admin_log.snackbar(it.mssg.toString())
                    }
                    is AllowShareAndRemoveApiState.Success->{
                        root_admin_log.snackbar(it.responce.message)
                        adminlogAdapter.notifyDataSetChanged()

                    }
                }
            }
        }
    }

}