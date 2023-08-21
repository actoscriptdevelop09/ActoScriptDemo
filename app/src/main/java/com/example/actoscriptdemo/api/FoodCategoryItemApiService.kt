package com.example.actoscriptdemo.api

import com.example.actoscriptdemo.model.CategoryData
import retrofit2.Call
import retrofit2.http.GET

interface FoodCategoryItemApiService {
    @GET("Foodcategory")
    fun getItems(): Call<String>
}