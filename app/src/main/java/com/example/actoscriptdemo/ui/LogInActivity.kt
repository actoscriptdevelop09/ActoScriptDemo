package com.example.actoscriptdemo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.actoscriptdemo.databinding.ActivityLogInBinding
import com.example.actoscriptdemo.model.SharePreference
import com.example.actoscriptdemo.viewModel.MyViewModel

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        checkUserIsLoginOrNot()

    }

    private fun handleClickEvents() {
        binding.btnLogIn.setOnClickListener {
            if (binding.etUserName.text.isEmpty()) {
                binding.etUserName.error = "Please enter userName"
            } else if (binding.etPassword.text.isEmpty()) {
                binding.etPassword.error = "Please enter password"
            } else if (binding.etUserName.text.isEmpty() && binding.etPassword.text.isEmpty()) {
                binding.etUserName.error = "Please enter userName"
                binding.etPassword.error = "Please enter password"
            } else {
                callLoginApi()
            }
        }
    }

    private fun callLoginApi() {
        SharePreference.saveUserName(this, "userName", binding.etUserName.text.toString())
        SharePreference.savePassword(this, "passWord", binding.etPassword.text.toString())
        viewModel.fetchLoginUser()

        if (viewModel.checkResponse == "Success") {
            SharePreference.saveLoginInfo(this, "logIn", true)
            finish()
            startActivity(Intent(this, HomeScreen::class.java))
        } else {
            Toast.makeText(this, viewModel.checkResponse, Toast.LENGTH_SHORT).show()
            Log.d("TAG", "callLoginApi: ${viewModel.checkResponse}")
        }
    }

    private fun checkUserIsLoginOrNot() {
        if (SharePreference.getLoginInfo(this, "logIn")!!) {
            finish()
            startActivity(Intent(this, HomeScreen::class.java))
        } else {
            handleClickEvents()
        }
    }
}