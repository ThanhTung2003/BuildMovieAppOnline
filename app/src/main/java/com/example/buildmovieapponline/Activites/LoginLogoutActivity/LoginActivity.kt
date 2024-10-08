package com.example.buildmovieapponline.Activites.LoginLogoutActivity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.buildmovieapponline.Activites.MainActivity
import com.example.buildmovieapponline.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener{
            val edituser = binding.editTextLogIn
            val editpass = binding.editTextLogIn
            if (edituser.text.toString().isEmpty() || editpass.text.toString().isEmpty()){
                Toast.makeText(this@LoginActivity, "please fill your username and password", Toast.LENGTH_SHORT).show()
            }else if(edituser.text.toString() == "1" || editpass.text.toString() == ""){
                val intent = Intent (this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this@LoginActivity, "Your username and password is incorrect", Toast.LENGTH_SHORT).show()

            }
        }
    }

}