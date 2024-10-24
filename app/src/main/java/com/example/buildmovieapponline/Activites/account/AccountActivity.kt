package com.example.buildmovieapponline.Activites.account

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.buildmovieapponline.Activites.LoginLogoutActivity.LoginActivity
import com.example.buildmovieapponline.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Khởi tạo SharedPreferences
        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)


        backAccount()
        buttonLogout()

    }

    private fun buttonLogout() {
        binding.logoutBtn.setOnClickListener {
            // Xóa trạng thái đăng nhập khỏi SharedPreferences
            val editor = sharedPreferences.edit()
            editor.clear() // Xóa tất cả dữ liệu trong SharedPreferences
            editor.apply() // Áp dụng thay đổi
            // Điều hướng về màn hình đăng nhập
            val intent = Intent(this@AccountActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            Log.d("check","dang xuat oke")
            finish() // Kết thúc AccountActivity để không còn lưu trong stack
        }
    }


    private fun backAccount() {
        binding.backAccount.setOnClickListener {
            finish()
        }
    }
}