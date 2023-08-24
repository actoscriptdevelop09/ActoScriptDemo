package com.example.actoscriptdemo.ui

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.actoscriptdemo.R
import com.example.actoscriptdemo.databinding.ActivityPaymentBinding
import com.example.actoscriptdemo.model.Constant

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getsharePrefsData()
        handleClickEvents()
    }

    private fun getsharePrefsData() {
        val itemList = Constant.getExistingList(this)
        itemList.forEach {
            Log.d("TAG", "getSharePrefsData__: ${it.PRICE}")
        }
        Log.d("TAG", "getSharePrefsData__size: ${itemList.size}")
    }

    private fun handleClickEvents() {
        binding.linearOnlinePayment.setOnClickListener {
            binding.imgQrCode.visibility = View.VISIBLE
            binding.linearPaymentOptions.visibility = View.GONE
        }
        binding.linearCODPayments.setOnClickListener {
            binding.imgQrCode.visibility = View.GONE
            binding.linearPaymentOptions.visibility = View.VISIBLE
        }
    }
}