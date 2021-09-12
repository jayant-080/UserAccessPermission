package com.jks.userrecognitionandaccesspermission.userauthui

import android.content.Context
import android.content.Intent
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
import com.jks.userrecognitionandaccesspermission.R
import com.jks.userrecognitionandaccesspermission.models.UserLoginModel
import com.jks.userrecognitionandaccesspermission.usermainui.UserMainActivity
import com.jks.userrecognitionandaccesspermission.utils.ApiStateSignin
import com.jks.userrecognitionandaccesspermission.utils.snackbar
import com.jks.userrecognitionandaccesspermission.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user_signin.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class UserSigninFragment:Fragment(R.layout.fragment_user_signin) {

    private val authViewModel:AuthViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_user_donthaveaccount.setOnClickListener {
            findNavController().navigate(
                UserSigninFragmentDirections.actionUserSigninFragmentToUserRegisterFragment()
            )
        }


        btn_user_login.setOnClickListener {
            val email = et_login_user_email.text.toString()
            val password = et_login_user_password.text.toString()
            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                root_login_user.snackbar("fill all required fields")
            }else{
                val prefs: SharedPreferences = requireActivity().getSharedPreferences("logindetails",
                    Context.MODE_PRIVATE
                )
                val isadmin = prefs.getString("isadmin",null)
                if(isadmin=="false"){
                    val user = UserLoginModel(email, password)
                    subscribeToObserber(user)
                }else{
                    root_login_user.snackbar("invalid login! you registered with admin")
                }


            }
        }
    }

    private fun subscribeToObserber(user:UserLoginModel) {
       authViewModel.signin(user)
        lifecycleScope.launchWhenStarted {
            authViewModel.authloginStateflow.collect {
              when(it){

                  is ApiStateSignin.Loading->{
                     pb_login_user.isVisible=true
                     btn_user_login.alpha= 0.5F
                      btn_user_login.isEnabled=false
                  }
                  is ApiStateSignin.Failure->{
                      pb_login_user.isVisible=false
                      btn_user_login.alpha= 1F
                      btn_user_login.isEnabled=true
                      root_login_user.snackbar(it.mssg.toString())
                      Log.d("bsdk",it.mssg.toString())
                  }
                  is ApiStateSignin.Success->{
                      pb_login_user.isVisible=false
                      btn_user_login.alpha= 1F
                      btn_user_login.isEnabled=true
                      root_login_user.snackbar(it.responce.message)
                      //saving data to shared prefrence
                      val pref: SharedPreferences =
                          requireActivity()
                              .getSharedPreferences(
                                  "logindetails",
                                  Context.MODE_PRIVATE
                              )
                      val editor: SharedPreferences.Editor = pref.edit()
                      editor.putString("token",it.responce.token)
                      editor.apply()

                      val gotousermain = Intent(requireActivity(),UserMainActivity::class.java)
                      startActivity(gotousermain)
                      requireActivity().finish()
                  }

              }
            }
        }

    }
}