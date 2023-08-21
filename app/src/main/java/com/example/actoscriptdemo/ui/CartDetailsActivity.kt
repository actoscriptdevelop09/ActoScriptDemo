package com.example.actoscriptdemo.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.actoscriptdemo.adapter.CartDetailsAdapter
import com.example.actoscriptdemo.api.DETAILS
import com.example.actoscriptdemo.databinding.ActivityCartDetailsBinding

class CartDetailsActivity : AppCompatActivity() {

    private val TAG = "CartDetailsActivity"
    private lateinit var binding : ActivityCartDetailsBinding
    private var localDataList: ArrayList<DETAILS> = ArrayList()
    private var liveDataList: ArrayList<DETAILS> = ArrayList()
    private lateinit var adapter: CartDetailsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getCartList()
        handleClickEvents()

    }

    private fun getCartList() {
        liveDataList = intent.getParcelableArrayListExtra("liveData")!!
        localDataList = intent.getParcelableArrayListExtra("cart")!!
        val hasDuplicates = removeDuplicatesById(localDataList)
        Log.d(TAG, "getCartList__size: ${hasDuplicates} __${liveDataList.size}")
        setCardDetails(hasDuplicates)
    }

    private fun setCardDetails(cartList: ArrayList<DETAILS>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CartDetailsAdapter(cartList)
        binding.recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        if (::adapter.isInitialized) {
            adapter.notifyDataSetChanged()

        }
    }
    
    private fun handleClickEvents(){
        binding.btnOrderNow.setOnClickListener {
            var existID :ArrayList<String> ?= ArrayList<String>()
            var liveId : String ?= null
            Log.d(TAG, "handleClickEvents____liveSize: ${liveDataList.size}")
//            liveDataList.forEach { liveID ->
//                existID!!.add(liveID.FOODCATEGORYITEMID!!)
//            liveId = liveID.FOODCATEGORYITEMID
//                Log.d(TAG, "handleClickEvents____live: ${liveID.FOODCATEGORYITEMID}___${liveID.ITEAMNAME}___")
//
//            }
            localDataList.forEach {

                if (it.FOODCATEGORYITEMID == liveId){
                    Log.d(TAG, "handleClickEvents____local__if: ${it.FOODCATEGORYITEMID}_____${liveId}_____${it.FOODCATEGORYITEMID}___${it.ITEAMNAME}___")
                }
                else{
                    Log.d(TAG, "handleClickEvents____local__if: ${it.FOODCATEGORYITEMID}___${it.ITEAMNAME}___")
                }
            }
        }
      
    }

    fun removeDuplicatesById(items: MutableList<DETAILS>): ArrayList<DETAILS> {
        val seenIds = HashSet<String>()
        val filteredList =ArrayList<DETAILS>()

        for (item in items) {
            if (!seenIds.contains(item.FOODCATEGORYITEMID)) {
                filteredList.add(item)
                seenIds.add(item.FOODCATEGORYITEMID!!)
            }
        }

        return filteredList
    }

}