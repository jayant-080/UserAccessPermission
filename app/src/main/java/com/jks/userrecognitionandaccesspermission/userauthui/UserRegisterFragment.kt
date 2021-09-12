package com.jks.userrecognitionandaccesspermission.userauthui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import com.jks.userrecognitionandaccesspermission.R
import com.jks.userrecognitionandaccesspermission.models.User
import com.jks.userrecognitionandaccesspermission.services.FirebaseService
import com.jks.userrecognitionandaccesspermission.utils.ApiState
import com.jks.userrecognitionandaccesspermission.utils.snackbar
import com.jks.userrecognitionandaccesspermission.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_admin_register.*
import kotlinx.android.synthetic.main.fragment_user_register.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserRegisterFragment: Fragment(R.layout.fragment_user_register) {

    private val authViewModel: AuthViewModel by viewModels()
    var appid:String?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseService.sharedPref=requireActivity().getSharedPreferences("sharedPref",Context.MODE_PRIVATE)
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            if(it.isSuccessful){
                FirebaseService.ntoken=it.result!!.token
                appid=it.result!!.token
            }else{
                root_user_register.snackbar("error")
            }
        }

        tv_user_alreadyaccount.setOnClickListener {
            findNavController().navigate(
                UserRegisterFragmentDirections.actionUserRegisterFragmentToUserSigninFragment()
            )
        }



        btn_user_signup.setOnClickListener {
            val name= et_reg_user_username.text.toString()
            val email = et_reg_user_email.text.toString()
            val password = et_reg_user_password.text.toString()


            if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                 root_user_register.snackbar("all fields are required")
            }else{
                val user = User(name,email,password,false,appid!!)
                subscribeToObserber(user)
            }

        }

    }

    private fun subscribeToObserber(user:User) {
        authViewModel.register(user)
        lifecycleScope.launchWhenStarted {
            authViewModel.authStateflow.collect {
              when(it){
                  is ApiState.Loading->{
                    pb_reg_user.isVisible=true
                    btn_user_signup.alpha = 0.5F
                      btn_user_signup.isEnabled=false
                  }
                  is ApiState.Failure->{
                      pb_reg_user.isVisible=false
                      btn_user_signup.alpha = 1F
                      btn_user_signup.isEnabled=true
                      val responce = it.mssg
                      root_user_register.snackbar(responce.toString())
                      Log.d("bsdk",responce.toString())
                  }
                  is ApiState.Success->{
                      pb_reg_user.isVisible=false
                      btn_user_signup.alpha = 1F
                      btn_user_signup.isEnabled=true
                      val responce = it.responce
                      root_user_register.snackbar(responce.message)
                      //saving data to shared prefrence
                      val pref: SharedPreferences =
                          requireActivity()
                              .getSharedPreferences(
                                  "logindetails",
                                  Context.MODE_PRIVATE
                              )
                      val editor: SharedPreferences.Editor = pref.edit()
                      editor.putString("id",responce.user.Id)
                      editor.putString("isadmin",responce.user.isadmin.toString())
                      editor.putString("name",responce.user.name)
                      editor.apply()
                  }
              }
            }
        }

    }
}