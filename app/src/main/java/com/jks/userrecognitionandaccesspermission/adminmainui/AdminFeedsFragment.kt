package com.jks.userrecognitionandaccesspermission.adminmainui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jks.userrecognitionandaccesspermission.R
import com.jks.userrecognitionandaccesspermission.adapter.FeedAdapter
import com.jks.userrecognitionandaccesspermission.utils.GetAllFeedState
import com.jks.userrecognitionandaccesspermission.utils.snackbar
import com.jks.userrecognitionandaccesspermission.viewmodels.UserMainViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_admin_feed.*
import kotlinx.android.synthetic.main.fragment_get_all_feed.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AdminFeedsFragment : Fragment(R.layout.fragment_admin_feed) {
    private val userMainViewmodel: UserMainViewmodel by viewModels()
    private var token:String?=null
    private var adminid:String?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs: SharedPreferences = requireActivity().getSharedPreferences("logindetails",
            Context.MODE_PRIVATE
        )
        token = prefs.getString("token",null)!!
        adminid = prefs.getString("id",null)!!
        fab_post_feed.setOnClickListener {
            findNavController().navigate(
                AdminFeedsFragmentDirections.actionAdminFeedsFragmentToAdminPostFeedFragment3()
            )
        }

        iv_setting_adminfeed.setOnClickListener {
            AdminFeedsFragmentDirections.actionAdminFeedsFragmentToUpdateProfilePic4()
        }
        userMainViewmodel.getAllFeedAdmin(adminid!!,token!!)
        subscribeToObserver()
    }
    private fun subscribeToObserver() {
        lifecycleScope.launchWhenStarted {
            userMainViewmodel.getallfeed.collect {
                when(it){
                    is GetAllFeedState.Loading->{
                        pb_adminfeed.isVisible=true
                    }
                    is GetAllFeedState.Failure->{
                        pb_adminfeed.isVisible=false
                        root_admin_feed.snackbar(it.mssg.toString())
                    }
                    is GetAllFeedState.Success->{
                        pb_adminfeed.isVisible=false
                        if(it.responce.feed!=null){
                            tv_admin_emptyfeed.isVisible= false
                            val feedadapter = FeedAdapter(requireActivity(),it.responce.feed!!)
                            rv_adminfeed.adapter=feedadapter
                            feedadapter.notifyDataSetChanged()
                        }else{
                            tv_admin_emptyfeed.isVisible= true
                        }

                    }
                }
            }

        }

    }
}