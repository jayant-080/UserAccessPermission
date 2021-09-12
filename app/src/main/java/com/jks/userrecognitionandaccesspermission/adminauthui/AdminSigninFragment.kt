package com.jks.userrecognitionandaccesspermission.adminauthui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jks.userrecognitionandaccesspermission.R
import com.jks.userrecognitionandaccesspermission.adminmainui.MainAdminActivity
import com.jks.userrecognitionandaccesspermission.models.UserLoginModel
import com.jks.userrecognitionandaccesspermission.utils.ApiStateSignin
import com.jks.userrecognitionandaccesspermission.utils.snackbar
import com.jks.userrecognitionandaccesspermission.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_admin_signin.*
import kotlinx.android.synthetic.main.fragment_user_signin.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AdminSigninFragment:Fragment(R.layout.fragment_admin_signin) {

    private val authViewModel: AuthViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_admin_donthaveaccount.setOnClickListener {
            findNavController().navigate(
                AdminSigninFragmentDirections.actionAdminSigninFragmentToAdminRegisterFragment()
            )
        }

        btn_admin_login.setOnClickListener {
            val email = et_login_admin_email.text.toString()
            val password = et_login_admin_password.text.toString()
            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                root_login_user.snackbar("fill all required fields")
            }else{
                val prefs: SharedPreferences = requireActivity().getSharedPreferences("logindetails",
                    Context.MODE_PRIVATE
                )
                val isadmin = prefs.getString("isadmin",null)
                if(isadmin=="true"){
                    val user = UserLoginModel(email, password)
                    subscribeToObserber(user)
                }else{
                    root_login_admin.snackbar("invalid login! you registered as user")
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
                        pb_login_admin.isVisible=true
                        btn_admin_login.alpha= 0.5F
                        btn_admin_login.isEnabled=false
                    }
                    is ApiStateSignin.Failure->{
                        pb_login_admin.isVisible=false
                        btn_admin_login.alpha= 1F
                        btn_admin_login.isEnabled=true
                        root_login_admin.snackbar(it.mssg.toString())
                    }
                    is ApiStateSignin.Success->{
                        pb_login_admin.isVisible=false
                        btn_admin_login.alpha= 1F
                        btn_admin_login.isEnabled=true
                        root_login_admin.snackbar(it.responce.message)
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

                        //sending to admin home activity after success signin
                        if(it.responce.success){
                            val gotomain = Intent(requireContext(),MainAdminActivity::class.java)
                            startActivity(gotomain)
                            requireActivity().finish()
                        }else{
                            Toast.makeText(requireContext(), "something went wrong! try again", Toast.LENGTH_SHORT).show()
                        }

                    }


                }
            }
        }

    }
}