package com.jks.userrecognitionandaccesspermission.adminauthui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.iid.FirebaseInstanceId
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

@AndroidEntryPoint
class AdminRegisterFragment: Fragment(R.layout.fragment_admin_register) {

    private val authViewModel: AuthViewModel by viewModels()
    var appid:String?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseService.sharedPref=requireActivity().getSharedPreferences("sharedPref",Context.MODE_PRIVATE)
        tv_admin_alreadyaccount.setOnClickListener {
            findNavController().navigate(
                AdminRegisterFragmentDirections.actionAdminRegisterFragmentToAdminSigninFragment()
            )
        }
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            if(it.isSuccessful){
                FirebaseService.ntoken=it.result!!.token
                appid=it.result!!.token
            }else{
                root_reg_admin.snackbar("error")
            }
        }
        btn_admin_signup.setOnClickListener {
            val name= et_reg_admin_username.text.toString()
            val email = et_reg_admin_email.text.toString()
            val password = et_reg_admin_password.text.toString()

            if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                root_reg_admin.snackbar("all fields are required")
            }else{
                val user = User(name, email, password,true,appid!!)
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
                        pb_reg_admin.isVisible=true
                        btn_admin_signup.alpha = 0.5F
                        btn_admin_signup.isEnabled = false
                    }
                    is ApiState.Failure->{
                        pb_reg_admin.isVisible=false
                        btn_admin_signup.alpha = 1F
                        btn_admin_signup.isEnabled = true
                        val responce = it.mssg
                        root_reg_admin.snackbar(responce.toString())
                    }
                    is ApiState.Success->{
                        pb_reg_admin.isVisible=false
                        btn_admin_signup.alpha = 1F
                        val responce = it.responce
                        btn_admin_signup.isEnabled = true
                        root_reg_admin.snackbar(responce.message)
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

                        //after registration sending to login
                        if(responce.success){
                            findNavController().navigate(
                                AdminRegisterFragmentDirections.actionAdminRegisterFragmentToAdminSigninFragment()
                            )
                        }
                    }
                }
            }
        }

    }
}