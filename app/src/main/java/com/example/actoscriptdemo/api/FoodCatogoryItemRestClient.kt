package com.example.actoscriptdemo.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FoodCatogoryItemRestClient {

    private val BASE_URL = "http://192.168.1.2/NavratriFoodCounterApi/api/"
  //      private val BASE_URL = "http://192.168.29.129/NavratriFoodCounterApi/api/"
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
