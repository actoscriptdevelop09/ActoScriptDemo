package com.example.actoscriptdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.actoscriptdemo.R
import com.example.actoscriptdemo.adapter.CategoryAdapter
import com.example.actoscriptdemo.adapter.OrderHistoryAdapter
import com.example.actoscriptdemo.databinding.ActivityOrderHistoryBinding
import com.example.actoscriptdemo.model.CategoryData
import com.example.actoscriptdemo.model.HistoryData

class OrderHistoryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOrderHistory()
    }

    private fun setOrderHistory() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<HistoryData>()
        for (i in 1..5) {
            data.add(HistoryData(1,"Cake Muffin", "Cake Muffin, pizza, burger, chocolate", "$150"))
        }
        val adapter = OrderHistoryAdapter(data)
        binding.recyclerView.adapter = adapter
    }
}