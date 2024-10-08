package com.blueray.platin.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.blueray.platin.databinding.ActivitySplashBinding
import com.blueray.platin.helpers.HelperUtils

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({

            if (HelperUtils.getUID(this) != "0" && HelperUtils.getUID(this) != "") {
                Log.e("***" , HelperUtils.getUID(this))
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
                finish()
            }
        }, 1000)
    }
}