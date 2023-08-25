package com.example.actoscriptdemo.api

import com.example.actoscriptdemo.model.CategoryData
import com.example.actoscriptdemo.model.PostData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface FoodCategoryItemApiService {
    @GET("Foodcategory")
    fun getItems(): Call<String>

    @Headers("Accept: aepplication/json",
        "Content-Type: application/json")
    @POST("Foodcategory")
    fun postItems(@Body postData: PostData): Call<String>


}