package com.example.actoscriptdemo.callBack

import com.example.actoscriptdemo.api.DETAILS

interface AddToCartClickListener {
    fun onItemFetched(
        item: DETAILS,
        position: Int,
        quantity: String?,
        foodcategoryitemid: String?,
        b: Boolean
    )
}