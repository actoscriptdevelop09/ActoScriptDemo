package com.example.actoscriptdemo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.actoscriptdemo.R
import com.example.actoscriptdemo.adapter.CategoryAdapter
import com.example.actoscriptdemo.adapter.MenuItemsAdapter
import com.example.actoscriptdemo.api.DETAILS
import com.example.actoscriptdemo.callBack.AddToCartClickListener
import com.example.actoscriptdemo.callBack.CategoryItemClickListener
import com.example.actoscriptdemo.databinding.ActivityMenuListBinding
import com.example.actoscriptdemo.model.Constant
import com.example.actoscriptdemo.viewModel.MyViewModel
import com.google.gson.Gson


class MenuListActivity : AppCompatActivity(), CategoryItemClickListener, AddToCartClickListener {

    private lateinit var binding: ActivityMenuListBinding
    private val TAG = "MainActivity"
    private val categoryItemList: ArrayList<DETAILS> = ArrayList()
    private val cartList: ArrayList<DETAILS> = ArrayList()
    private var sharePrefsList: ArrayList<DETAILS> = ArrayList()
    private val firstCategoryItemList: ArrayList<DETAILS> = ArrayList()
    private var passItemList: DETAILS = DETAILS()
    private val itemList: ArrayList<DETAILS> = ArrayList()
    var stateList = ArrayList<String>()
    var stateIdList = ArrayList<String>()
    private lateinit var itemAdapter: MenuItemsAdapter
    private lateinit var viewModel: MyViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        getAPIResponse()
        handleClickEvents()

    }

    private fun setCategoryView(categoryList: ArrayList<DETAILS>) {

        binding.recyclerCategory.layoutManager = LinearLayoutManager(this)
        var stateId: String? = null

        for (i in categoryList.indices) {
            val stateNAme = categoryList[i].FOODCATEGORYNAME
            stateList.add(stateNAme!!)
            Log.d(
                TAG,
                "setCategoryView___: ${stateNAme}___${stateList.size}___${stateList.distinct()}"
            )
            stateId = categoryList[i].FOODCATEGORYID
            stateIdList.add(stateId!!)
        }
        val adapter = CategoryAdapter(categoryList, stateList.distinct(), stateIdList.distinct())
        binding.recyclerCategory.adapter = adapter
        adapter.setOnItemClickListener(this)
        adapter.selectedPosition = 0

    }

    private fun setItemView(itemList: ArrayList<DETAILS>) {
        Log.d(TAG, "setItemView: ${itemList.size}")
        // binding.recyclerItems.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerItems.layoutManager = LinearLayoutManager(this)
        itemAdapter = MenuItemsAdapter(itemList)
        binding.recyclerItems.adapter = itemAdapter
        val anim: Animation = AnimationUtils.loadAnimation(this, R.anim.anim_bottom_to_top)
        binding.recyclerItems.startAnimation(anim)
        itemAdapter.setOnCartClickListener(this)

    }

    override fun onDataFetched(
        stateId: String, stateName: String, position: Int, textview: TextView
    ) {
        itemList.clear()
        val cityList = ArrayList<String>()
        categoryItemList.forEach {
            cityList.add(it.ITEAMNAME!!)
            if (stateId == it.FOODCATEGORYID) {
                itemList.add(it)
                setItemView(itemList)
                Log.d(
                    TAG,
                    "onDataFetched______: ${itemList.size} ${stateName}___${it.ITEAMNAME}___"
                )

            }
        }
    }

    private fun getAPIResponse() {
        viewModel.fetchDataFromApi()

        categoryItemList.clear()
        viewModel.mLiveDataList.observe(this) {
            if (it != null) {
                val detailList = it
                detailList.forEach {
                    categoryItemList.add(it)
                }
                //first category data set only
                var previousId: String? = null
                for (item in categoryItemList) {
                    if (previousId == null || previousId == item.FOODCATEGORYID) {
                        firstCategoryItemList.add(item)
                        previousId = item.FOODCATEGORYID
                        Log.d(
                            TAG,
                            "onResponse__detailList__: ${firstCategoryItemList.size}"
                        )

                    } else {
                        Log.d(TAG, "onResponse__detailList__: Break")
                        break // Stop loading when ID changes
                    }
                }
                setCategoryView(categoryItemList)
                setItemView(firstCategoryItemList)
            }

        }

    }

    private fun handleClickEvents() {
        binding.imgCart.setOnClickListener {

            val intent = Intent(this, CartDetailsActivity::class.java)
            intent.putParcelableArrayListExtra("liveData", categoryItemList)
            Log.d(TAG, "onItemFetched____check__pass: ${Constant.getExistingList(this)}")
            Log.d(TAG, "onItemFetched____check__pass: ${sharePrefsList.size}")
            if (sharePrefsList.size == 0) {
                intent.putParcelableArrayListExtra("cart", Constant.getExistingList(this))
            } else {
                intent.putParcelableArrayListExtra("cart", sharePrefsList)
            }
            startActivity(intent)
        }
    }

    override fun onItemFetched(
        item: DETAILS,
        position: Int,
        quantity: String?,
        foodcategoryitemid: String?,
        check: Boolean
    ) {
        if (check) {
            cartList.add(item)
            cartList.filter { it.FOODCATEGORYITEMID != foodcategoryitemid }
            Log.d(
                TAG,
                "onItemFetched____check__if__1 : ${cartList.size}___ ${check}___${sharePrefsList.size}"
            )
        } else {
            cartList.remove(item)
            Log.d(
                TAG,
                "onItemFetched____check__else : ${cartList.size}___ ${check}___${sharePrefsList.size}"
            )
        }
        sharePrefsList = Constant.addNewList(this, cartList, quantity)
        
    }

    override fun onResume() {
        super.onResume()
        if (::itemAdapter.isInitialized) {
            itemAdapter.notifyDataSetChanged()
        }
    }

}
