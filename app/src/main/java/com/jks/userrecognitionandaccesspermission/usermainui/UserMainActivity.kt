package com.jks.userrecognitionandaccesspermission.usermainui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jks.userrecognitionandaccesspermission.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserMainActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_main)
    }
}