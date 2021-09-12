package com.jks.userrecognitionandaccesspermission.usermainui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jks.userrecognitionandaccesspermission.R
import com.jks.userrecognitionandaccesspermission.adapter.FeedAdapter
import com.jks.userrecognitionandaccesspermission.utils.GetAllFeedState
import com.jks.userrecognitionandaccesspermission.utils.snackbar

import com.jks.userrecognitionandaccesspermission.viewmodels.UserMainViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_get_all_feed.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class GetFeeds:Fragment(R.layout.fragment_get_all_feed) {

    private val userMainViewmodel: UserMainViewmodel by viewModels()
    private var token:String?=null
    private var adminid:String?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs: SharedPreferences = requireActivity().getSharedPreferences("logindetails",
            Context.MODE_PRIVATE
        )
        token = prefs.getString("token",null)!!
        adminid=GetFeedsArgs.fromBundle(requireArguments()).adminid
        userMainViewmodel.getAllFeed(adminid!!,token!!)
        subscribeToObserver()
    }

    private fun subscribeToObserver() {
         lifecycleScope.launchWhenStarted {
             userMainViewmodel.getallfeed.collect {
                 when(it){
                     is GetAllFeedState.Loading->{
                         pb_getfeed.isVisible=true
                     }
                     is GetAllFeedState.Failure->{
                         pb_getfeed.isVisible=false
                         root_getfeed.snackbar(it.mssg.toString())
                     }
                     is GetAllFeedState.Success->{
                         pb_getfeed.isVisible=false
                         val feedadapter = FeedAdapter(requireActivity(),it.responce.feed!!)
                         rv_getfeed.adapter=feedadapter
                         feedadapter.notifyDataSetChanged()
                     }
                 }
             }

         }

    }
}