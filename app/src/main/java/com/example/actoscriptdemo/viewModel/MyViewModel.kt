package com.example.actoscriptdemo.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.actoscriptdemo.api.DETAILS
import com.example.actoscriptdemo.api.FoodCategoryItemApiService
import com.example.actoscriptdemo.api.FoodCategoryItemResponse
import com.example.actoscriptdemo.api.FoodCatogoryItemRestClient
import com.example.actoscriptdemo.api.LoginApiService
import com.example.actoscriptdemo.api.LoginRestclient
import com.example.actoscriptdemo.model.SharePreference
import com.example.actoscriptdemo.model.MyApp
import com.example.actoscriptdemo.model.PostData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyViewModel : ViewModel() {

    private val liveDataList = MutableLiveData<ArrayList<DETAILS>>()
    val mLiveDataList: LiveData<ArrayList<DETAILS>> get() = liveDataList
    private val TAG = "MyViewModel"
    val mApiService: FoodCategoryItemApiService =
        FoodCatogoryItemRestClient.client.create(FoodCategoryItemApiService::class.java)
    var checkResponse : String ?= null
    fun fetchDataFromApi() {

        val call = mApiService.getItems()
        call.enqueue(object : Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                //    categoryItemList.clear()
                    if (response.isSuccessful) {
                        // Remove the 'string xmlns' element
                        val startIndex = responseData?.indexOf('{')
                        val endIndex = responseData?.lastIndexOf('}')
                        val jsonResponse =
                            responseData?.substring(startIndex ?: 0, (endIndex ?: 0) + 1)

                        val serviceResponse =
                            Gson().fromJson(jsonResponse, FoodCategoryItemResponse::class.java)
                        Log.d(TAG, "onResponse____sucess--->: ${serviceResponse.SERVICERESPONSE}")
                        if (serviceResponse != null) {
                            val detailList = serviceResponse.SERVICERESPONSE!!.DETAILSLIST!!.DETAILS
                                liveDataList.value = detailList
                            Log.d(TAG, "onResponse____sucess-->: ${liveDataList.value!!.size}")
                        }

                    }
                } else {
                    Toast.makeText(
                        MyApp.instance.applicationContext,
                        "API response not success",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                Toast.makeText(MyApp.instance.applicationContext, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun passItemListToConfirmOrder(postData: PostData) {
        val call = mApiService.postItems(postData)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d(TAG, "onResponse___________: ${response.body()}")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun fetchLoginUser(){
        val mApiService: LoginApiService =
            LoginRestclient.client.create(LoginApiService::class.java)

        val stringStringMap = HashMap<String, String>().apply {
            put("UserName", SharePreference.getUserName(MyApp().getAppContext()!!,"userName")!!)
            put("Password", SharePreference.getPassword(MyApp().getAppContext()!!,"passWord")!!)
        }

        val call = mApiService.postLogInData(stringStringMap)
        call?.enqueue(object : Callback<String?>{
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                if (response.isSuccessful){
                    val responseData = response.body()
                    checkResponse = "Success"
                    val startIndex = responseData?.indexOf('{')
                    val endIndex = responseData?.lastIndexOf('}')
                    val jsonResponse =
                        responseData?.substring(startIndex ?: 0, (endIndex ?: 0) + 1)
                    val serviceResponse =
                        Gson().fromJson(jsonResponse, FoodCategoryItemResponse::class.java)
                    Log.d(TAG, "onResponse___body: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                checkResponse = t.message
            }

        })
    }

}