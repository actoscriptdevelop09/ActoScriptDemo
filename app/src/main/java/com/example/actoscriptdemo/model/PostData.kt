package com.example.actoscriptdemo.model

import com.example.actoscriptdemo.api.DETAILS

data class PostData(
    val OPER: String,
    val PAYMENTTYPE: String,
    val CASHTYPE: String,
    val ORDERFLAG: String,
    val BILLAMOUNT: String,
    val PAYAMOUNT: String,
    val PENDINGAMOUNT: String,
    val Food_Categorydetails: ArrayList<DETAILS>
)