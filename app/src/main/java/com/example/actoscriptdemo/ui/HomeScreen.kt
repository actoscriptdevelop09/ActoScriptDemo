package com.example.actoscriptdemo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.actoscriptdemo.R
import com.example.actoscriptdemo.databinding.HomeScreenBinding

class HomeScreen : AppCompatActivity() {
    lateinit var binding : HomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getAnimation()
        handleClick()
    }

    private fun getAnimation() {
        val anim: Animation = AnimationUtils.loadAnimation(this, R.anim.anim_bottom_to_top)
        binding.linearMain.startAnimation(anim)
    }

    private fun handleClick() {

        val anim: Animation = AnimationUtils.loadAnimation(this, R.anim.anim_bottom_to_top)
        binding.linearMain.startAnimation(anim)

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

    override fun onResume() {
        super.onResume()
        getAnimation()
    }
}