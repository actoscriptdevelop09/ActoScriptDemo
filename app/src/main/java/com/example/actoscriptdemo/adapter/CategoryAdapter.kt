package com.example.actoscriptdemo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.actoscriptdemo.R
import com.example.actoscriptdemo.api.DETAILS
import com.example.actoscriptdemo.callBack.CategoryItemClickListener


class CategoryAdapter(
    private val mList: ArrayList<DETAILS>,
    private val stateList: List<String>,
    private val stateIdList: List<String>
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private lateinit var onClick: CategoryItemClickListener
    private lateinit var context : Context
    var selectedPosition = RecyclerView.NO_POSITION

    fun setOnItemClickListener(itemClickListener: CategoryItemClickListener) {
        this.onClick = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {

        val itemsViewModel =    stateList[position]
            holder.textView.text = itemsViewModel

        if (position == selectedPosition) {
            val anim: Animation = AnimationUtils.loadAnimation(context, R.anim.anim_left_to_right)
            holder.textView.startAnimation(anim)
            holder.textView.setBackgroundResource(R.drawable.bg_login_button)
        } else {
            holder.textView.setBackgroundResource(R.color.white)
        }

        holder.linearCategory.setOnClickListener {
            if (selectedPosition != position) {
                selectedPosition = position
                notifyDataSetChanged()
                Log.d("TAG", "onBindViewHolder__stateId: ${stateIdList!![position]}")
                onClick.onDataFetched(stateIdList[position],itemsViewModel, position, holder.textView)
            }
        }

    }

    override fun getItemCount(): Int {
        return stateList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.txtCategory)
        val linearCategory: LinearLayout = itemView.findViewById(R.id.linearCategory)
    }

}