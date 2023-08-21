package com.example.actoscriptdemo.model

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.actoscriptdemo.api.DETAILS
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Constant {

    var existingList = ArrayList<DETAILS>()

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

        return existingList ?: ArrayList()
    }

    // Add a new list to the existing list and save to SharedPreferences
    fun addNewList(
        context: Context, newList: List<DETAILS>,
        quantity: String?
    ): ArrayList<DETAILS> {
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

}