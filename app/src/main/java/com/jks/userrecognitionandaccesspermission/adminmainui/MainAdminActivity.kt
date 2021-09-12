package com.jks.userrecognitionandaccesspermission.adminmainui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.jks.userrecognitionandaccesspermission.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main_admin.*

@AndroidEntryPoint
class MainAdminActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_admin)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.adminmainhost) as NavHostFragment

        bottomNavigationView.apply {
            setupWithNavController(navHostFragment.findNavController())
            setOnNavigationItemReselectedListener { Unit }
        }
    }
}