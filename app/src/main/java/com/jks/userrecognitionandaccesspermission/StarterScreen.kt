package com.jks.userrecognitionandaccesspermission

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.jks.userrecognitionandaccesspermission.adminauthui.MainAdminAuthActivity
import com.jks.userrecognitionandaccesspermission.userauthui.MainUserAuthActivity
import kotlinx.android.synthetic.main.activity_starter.*

class StarterScreen:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starter)

        btn_gofor_user.setOnClickListener {
           val gotoUserAuth = Intent(this@StarterScreen,MainUserAuthActivity::class.java)
            startActivity(gotoUserAuth)
            finish()
        }

        btn_gofor_admin.setOnClickListener {
            val gotoUserAuth = Intent(this@StarterScreen,MainAdminAuthActivity::class.java)
            startActivity(gotoUserAuth)
            finish()
        }

    }
}