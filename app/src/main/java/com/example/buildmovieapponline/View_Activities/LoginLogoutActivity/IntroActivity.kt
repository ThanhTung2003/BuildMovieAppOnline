package com.example.buildmovieapponline.View_Activities.LoginLogoutActivity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.buildmovieapponline.Const.AppPreferences.Companion.CHECK
import com.example.buildmovieapponline.Const.AppPreferences.Companion.ISLOGGEDIN
import com.example.buildmovieapponline.Const.AppPreferences.Companion.MYAPPPREFS
import com.example.buildmovieapponline.View_Activities.MainActivity
import com.example.buildmovieapponline.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding
    private lateinit var sharedPreferences: SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(1838)
        installSplashScreen()
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(MYAPPPREFS, MODE_PRIVATE)

        //kiem tra dang nhap
        val isLoggedIn = sharedPreferences.getBoolean(ISLOGGEDIN, false)
        if (isLoggedIn) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Log.d(CHECK, "da co tai khoan")
            finish()
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            Log.d(CHECK, "chua co tai khoan")
            finish()
        }

        binding.introBtn.setOnClickListener {
            val intent = Intent(this@IntroActivity, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}