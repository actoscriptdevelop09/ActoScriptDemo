package com.example.actoscriptdemo.model

import android.content.Context
import android.util.Log
import com.example.actoscriptdemo.api.DETAILS
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Constant {

    var socketUrl = "https://myfirstscoket-crickacto.onrender.com/"

    fun saveItem(context: Context, key: String, value: String) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getItem(context: Context, key: String): String? {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, "")
    }

    fun getExistingList(context: Context): ArrayList<DETAILS> {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val json = sharedPreferences.getString("data_", "")

        val gson = Gson()
        val existingList = gson.fromJson<ArrayList<DETAILS>>(
            json!!,
            object : TypeToken<ArrayList<DETAILS>>() {}.type
        )

        Log.d("TAG", "getExistingList+--: $existingList")

        return existingList ?: ArrayList()
    }

    // Add a new list to the existing list and save to SharedPreferences
    fun addNewList(
        context: Context, newList: List<DETAILS>, check : String
    ): ArrayList<DETAILS> {
        var existingList = ArrayList<DETAILS>()

        if (check == "add"){
            existingList = getExistingList(context)
            var id: String? = null
            if (newList.isNotEmpty()) {

                newList.forEach {
                    existingList?.add(it)
                }
                Log.d(
                    "SharePrefs",
                    "getExistingList__id__1___if: ${existingList.size}___${newList.size}"
                )
            }
        }
        else{
            existingList.addAll(newList)
        }

        Log.d("SharePrefs", "getExistingList: ${existingList.size}___${newList.size}")
        val sharedPreferences =
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val updatedJson = gson.toJson(existingList)
        val editor = sharedPreferences.edit()
        editor.putString("data_", updatedJson)
        Log.d("SharePrefs", "addNewList___to_sharePrefs: $updatedJson")
        editor.apply()
        return existingList
    }

    fun saveButtonClick(context: Context, key: String, value: Boolean) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getButtonClick(context: Context, key: String): Boolean? {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, true)
    }

    fun saveItemPrice(context: Context, key: String, value: Int) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getItemPrice(context: Context, key: String): Int? {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt(key, 0)
    }

    fun saveCategoryItemList(context: Context,mList: ArrayList<DETAILS>){
        val sharedPreferences =
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val updatedJson = gson.toJson(mList)
        val editor = sharedPreferences.edit()
        editor.putString("mainList", updatedJson)
        Log.d("SharePrefs", "addNewList___to_sharePrefs: $updatedJson")
        editor.apply()
    }

    fun getCategoryItemList(context: Context): ArrayList<DETAILS> {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val json = sharedPreferences.getString("mainList", "")

        val gson = Gson()
        val existingList = gson.fromJson<ArrayList<DETAILS>>(
            json!!,
            object : TypeToken<ArrayList<DETAILS>>() {}.type
        )

        Log.d("TAG", "getExistingList+--: $existingList")

        return existingList ?: ArrayList()
    }

}