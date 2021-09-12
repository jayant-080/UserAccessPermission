package com.jks.userrecognitionandaccesspermission

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.myLooper()!!).postDelayed({
            val gotostarter= Intent(this@SplashScreen,StarterScreen::class.java)
            startActivity(gotostarter)
            finish()
        },2000L)
    }
}