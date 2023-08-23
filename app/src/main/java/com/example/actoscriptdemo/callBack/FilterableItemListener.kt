package com.example.actoscriptdemo.callBack

import com.example.actoscriptdemo.api.DETAILS

interface FilterableItemListener {
    fun onDataFetched(mList: DETAILS, position: Int)

}