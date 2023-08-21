package com.example.actoscriptdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.actoscriptdemo.R
import com.example.actoscriptdemo.adapter.LedgerAdapter
import com.example.actoscriptdemo.adapter.OrderHistoryAdapter
import com.example.actoscriptdemo.databinding.ActivityLedgerBinding
import com.example.actoscriptdemo.databinding.ActivityOrderHistoryBinding
import com.example.actoscriptdemo.model.HistoryData
import com.example.actoscriptdemo.model.LedgerData

class LedgerActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLedgerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLedgerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOrderHistory()
    }

    private fun setOrderHistory() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<LedgerData>()
        for (i in 1..5) {
            data.add(LedgerData(1,"Actocript", "01 Jan, 01:11 AM", "$150"))
        }
        val adapter = LedgerAdapter(data)
        binding.recyclerView.adapter = adapter
    }

}