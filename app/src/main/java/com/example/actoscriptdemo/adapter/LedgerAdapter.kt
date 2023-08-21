package com.example.actoscriptdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.actoscriptdemo.R
import com.example.actoscriptdemo.model.HistoryData
import com.example.actoscriptdemo.model.LedgerData

class LedgerAdapter(private val mList: List<LedgerData>) :
    RecyclerView.Adapter<LedgerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ledger, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]
        holder.tvItemName.text = itemsViewModel.userName
        holder.tvItemContent.text = itemsViewModel.dateTime
        holder.tvItemPrice.text = itemsViewModel.itemPrise
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvItemName: TextView = itemView.findViewById(R.id.tvUserName)
        val tvItemContent: TextView = itemView.findViewById(R.id.tvDateTime)
        val tvItemPrice: TextView = itemView.findViewById(R.id.tvPrice)
    }
}