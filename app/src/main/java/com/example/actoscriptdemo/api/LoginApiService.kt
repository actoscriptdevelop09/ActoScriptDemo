package com.example.actoscriptdemo.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap


interface LoginApiService {
    @GET("Security_Userlogin_Authentication")
    fun postLogInData(
        @HeaderMap headers: HashMap<String, String>
    ): Call<String?>?
}
