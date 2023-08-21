package com.example.actoscriptdemo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.actoscriptdemo.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleClickEvents()
    }

    private fun handleClickEvents() {
        binding.btnLogIn.setOnClickListener {
            startActivity(Intent(this, HomeScreen::class.java))

//            if (binding.etUserName.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()){
//                startActivity(Intent(this, HomeScreen::class.java))
//            }
//            else if (binding.etUserName.text.isEmpty()){
//                binding.etUserName.error = "Please enter userName"
//            }
//            else if (binding.etPassword.text.isEmpty()){
//                binding.etPassword.error = "Please enter password"
//            }
//            else{
//                binding.etUserName.error = "Please enter userName"
//                binding.etPassword.error = "Please enter password"
//            }
        }
    }
}