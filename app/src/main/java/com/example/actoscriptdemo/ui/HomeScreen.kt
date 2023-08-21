package com.example.actoscriptdemo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.actoscriptdemo.databinding.HomeScreenBinding

class HomeScreen : AppCompatActivity() {
    lateinit var binding : HomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleClick()
    }

    private fun handleClick() {
        binding.linearMenu.setOnClickListener {
            startActivity(Intent(this,MenuListActivity::class.java))
        }
        binding.linearHistory.setOnClickListener {
            startActivity(Intent(this,OrderHistoryActivity::class.java))
        }
        binding.linearLedger.setOnClickListener {
            startActivity(Intent(this, LedgerActivity::class.java))
        }
    }
}