package com.example.actoscriptdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.actoscriptdemo.R
import com.example.actoscriptdemo.model.HistoryData

class OrderHistoryAdapter(private val mList: List<HistoryData>) :
    RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_history, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]
        holder.tvItemName.text = itemsViewModel.itemName
        holder.tvItemContent.text = itemsViewModel.itemContent
        holder.tvItemPrice.text = itemsViewModel.itemPrice
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvItemName: TextView = itemView.findViewById(R.id.txtItemName)
        val tvItemContent: TextView = itemView.findViewById(R.id.txtItemContent)
        val tvItemPrice: TextView = itemView.findViewById(R.id.txtPrice)
    }
}