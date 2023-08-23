package com.example.actoscriptdemo.ui

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.actoscriptdemo.adapter.CartDetailsAdapter
import com.example.actoscriptdemo.api.DETAILS
import com.example.actoscriptdemo.callBack.FilterableItemListener
import com.example.actoscriptdemo.databinding.ActivityCartDetailsBinding
import com.example.actoscriptdemo.model.Constant
import com.example.actoscriptdemo.viewModel.MyViewModel

class CartDetailsActivity : AppCompatActivity(), FilterableItemListener {

    private val TAG = "CartDetailsActivity"
    private lateinit var binding: ActivityCartDetailsBinding
    private var localDataList: ArrayList<DETAILS> = ArrayList()
    private var filterLocalDataList: ArrayList<DETAILS> = ArrayList()
    private var localDataListFromCallBack: ArrayList<DETAILS> = ArrayList()
    private var removeFromCallBack: ArrayList<DETAILS> = ArrayList()
    private var liveDataList: ArrayList<DETAILS> = ArrayList()
    private lateinit var adapter: CartDetailsAdapter
    private var positonList: ArrayList<Int> = ArrayList()
    private lateinit var viewModel: MyViewModel
    val listWithLiveId = ArrayList<DETAILS>()
    val listWithoutLiveId = ArrayList<DETAILS>()
    var newList = mutableListOf<DETAILS>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        getCartList()

    }

    private fun getCartList() {
        localDataList = intent.getParcelableArrayListExtra("cart")!!
        filterLocalDataList = removeDuplicatesById(localDataList)
        Log.d(TAG, "getCartList__localDataList: ${localDataList}")
        Log.d(TAG, "getCartList__size: ${filterLocalDataList}")
        Log.d(TAG, "getCartList__sharePrefs: ${Constant.getExistingList(this)}")
        setCardDetails(filterLocalDataList)
        liveDataList = getAPIResponse()
    }

    private fun getAPIResponse(): ArrayList<DETAILS> {
        viewModel.fetchDataFromApi()
        var liveDataList: ArrayList<DETAILS> = ArrayList()
        viewModel.mLiveDataList.observe(this) {
            if (it != null) {
                val detailList = it
                detailList.forEach {
                    liveDataList.add(it)
                }
            }
        }
        Log.d(TAG, "handleClickEvents____liveSize_getAPIResponse: ${liveDataList.size}")
        return liveDataList
    }

    private fun setCardDetails(cartList: ArrayList<DETAILS>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        Log.d(TAG, "setCardDetails: ${newList.size}___${cartList.size}")
        adapter = if (newList.isNotEmpty()){
            CartDetailsAdapter(newList)
        } else{
            CartDetailsAdapter(cartList)
        }

        binding.recyclerView.adapter = adapter
        adapter.setItemListener(this)
        handleClickEvents()
    }


    override fun onResume() {
        super.onResume()
        if (::adapter.isInitialized) {
            adapter.notifyDataSetChanged()
        }
    }

    private fun handleClickEvents() {
        binding.btnOrderNow.setOnClickListener {
            if (::adapter.isInitialized) {
                adapter.setItemListener(this)
            }

            Log.d(TAG, "handleClickEvents____liveSize: ${liveDataList.size}")
            Log.d(TAG, "handleClickEvents____filterLocalDataList: ${localDataListFromCallBack}")

            val liveId: ArrayList<String> = ArrayList()
            liveDataList.forEach {
                liveId.add(it.FOODCATEGORYITEMID!!)
            }

            if (localDataListFromCallBack != null &&
                localDataListFromCallBack.isNotEmpty() &&
                liveId != null && liveId.isNotEmpty()
            ) {
                listWithLiveId.clear()
                listWithoutLiveId.clear()

                Log.d(TAG, "handleClickEvents____local__if__1: ${localDataListFromCallBack.size}___${listWithLiveId.size}___${listWithoutLiveId.size}")

                for (item in localDataListFromCallBack) {
                    if (liveId.contains(item.FOODCATEGORYITEMID)) {
                        listWithLiveId.add(item)
                    } else {
                        localDataListFromCallBack.remove(item)
                        listWithoutLiveId.add(item)
                    }
                }
                Log.d(TAG, "handleClickEvents____local__if__2:${localDataListFromCallBack.size}___ ${listWithLiveId.size}___${listWithoutLiveId.size}")

                if (listWithoutLiveId.isNotEmpty()){
                    showAlertDialog(listWithLiveId)
                }
                else{
                    val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.clear()
                    editor.apply()
                    finish()
                    startActivity(Intent(this,PaymentActivity::class.java))
                }
            }

        }
    }

    private fun removeDuplicatesById(items: MutableList<DETAILS>): ArrayList<DETAILS> {
        val seenIds = HashSet<String>()
        val filteredList = ArrayList<DETAILS>()

        for (item in items) {
            if (!seenIds.contains(item.FOODCATEGORYITEMID)) {
                filteredList.add(item)
                seenIds.add(item.FOODCATEGORYITEMID!!)
            }
        }

        return filteredList
    }

    override fun onDataFetched(mList: DETAILS, position: Int) {

        localDataListFromCallBack.add(mList)
        positonList.add(position)
        Log.d(TAG, "onDataFetched: ${mList.ITEAMNAME}")
        getTotalPrice(localDataListFromCallBack)

    }

    private fun showAlertDialog(mutableList: MutableList<DETAILS>) {

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Alert")
        alertDialogBuilder.setMessage("This is an example of an AlertDialog with only a positive button.")
        alertDialogBuilder.setPositiveButton(
            "OK",
            DialogInterface.OnClickListener { dialog, which ->
                Log.d(TAG, "showAlertDialog1: ${mutableList.size}")

                newList = Constant.updateListWithNewData(this, mutableList)
                Log.d(TAG, "showAlertDialog___size: ${newList.size}")
                newList.forEach {
                    Log.d(TAG, "showAlertDialog___size: ${it.ITEAMNAME}")
                }
                adapter.setFilterList(newList)
                adapter.notifyDataSetChanged()
                binding.recyclerView.invalidate()
                dialog.dismiss() // Close the dialog

            })

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun getTotalPrice(
        localDataListFromCallBack: ArrayList<DETAILS>
    ) {
        val list = arrayListOf(localDataListFromCallBack)
        Log.d(TAG, "getTotalPrice__: ${list.flatten().distinct()}")
        Log.d(TAG, "getTotalPrice__l: ${localDataListFromCallBack.distinct().size}")

        list.flatten().distinct().forEach {
            val finalPrice = Constant.getItemPrice(this, "price_${it.FOODCATEGORYITEMID}")
            Log.d(TAG, "getTotalPrice: $finalPrice")
        }

    }

}