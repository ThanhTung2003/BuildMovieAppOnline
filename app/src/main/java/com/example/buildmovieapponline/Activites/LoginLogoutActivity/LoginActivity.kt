package com.example.buildmovieapponline.Activites.LoginLogoutActivity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.buildmovieapponline.Activites.MainActivity
import com.example.buildmovieapponline.Model.RetrofitClient
import com.example.buildmovieapponline.ModelApi.DataLogin.LoginRequest
import com.example.buildmovieapponline.ModelApi.DataLogin.LoginResponse
import com.example.buildmovieapponline.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Khởi tạo SharedPreferences
        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)

        buttonLogin()
    }

    private fun buttonLogin() {
        binding.loginBtn.setOnClickListener{
            val username = binding.editTextLogIn.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()){
                Toast.makeText(this@LoginActivity,"Vui lòng điền tên đăng nhập và mật khẩu",Toast.LENGTH_SHORT).show()
            }else{
                binding.progressBarLogin.visibility = View.VISIBLE
                login(username,password)
            }
        }
    }

    private fun login(username: String, password: String) {
        val loginRequest = LoginRequest(username, password)

        RetrofitClient.instance.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body()?.status == "success") {
                    // Lưu trạng thái đăng nhập hoặc token nếu có
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.apply()
                    binding.progressBarLogin.visibility = View.VISIBLE

                    //login thanh cong
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, response.body()?.body ?: "Login failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Login failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}