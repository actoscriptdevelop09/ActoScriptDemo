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
import com.example.actoscriptdemo.model.MyApp
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyViewModel : ViewModel() {

    private val liveDataList = MutableLiveData<ArrayList<DETAILS>>()
    val mLiveDataList: LiveData<ArrayList<DETAILS>> get() = liveDataList
    private val TAG = "MyViewModel"

    fun fetchDataFromApi() {
        val mApiService: FoodCategoryItemApiService =
            FoodCatogoryItemRestClient.client.create(FoodCategoryItemApiService::class.java);
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

                            //first category data set only
//                            var previousId: String? = null
//                            for (item in detailList) {
//                                if (previousId == null || previousId == item.FOODCATEGORYID) {
//                                    firstCategoryItemList.add(item)
//                                    previousId = item.FOODCATEGORYID
//                                    Log.d(
//                                        TAG,
//                                        "onResponse__detailList__: ${firstCategoryItemList.size}"
//                                    )
//
//                                } else {
//                                    Log.d(TAG, "onResponse__detailList__: Break")
//                                    break // Stop loading when ID changes
//                                }
//                            }
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
}