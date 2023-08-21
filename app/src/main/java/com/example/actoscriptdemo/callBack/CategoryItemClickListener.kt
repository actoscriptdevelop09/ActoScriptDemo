package com.example.actoscriptdemo.callBack

import android.widget.TextView

interface CategoryItemClickListener {
    fun onDataFetched(stateId: String, data: String, position: Int, linearCategory: TextView)
}