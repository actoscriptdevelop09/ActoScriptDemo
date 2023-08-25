package com.example.actoscriptdemo.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object LoginRestclient {

    private val BASE_URL = " http://27.116.48.79/NavratriFoodCounterApi/api/"
    private var mRetrofit: Retrofit? = null

    val client: Retrofit
        get() {

            if (mRetrofit == null) {
                mRetrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return this.mRetrofit!!
        }


}