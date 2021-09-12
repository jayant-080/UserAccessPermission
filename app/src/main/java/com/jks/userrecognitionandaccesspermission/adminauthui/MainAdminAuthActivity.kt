package com.jks.userrecognitionandaccesspermission.adminauthui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jks.userrecognitionandaccesspermission.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainAdminAuthActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_admin_auth)
    }
}