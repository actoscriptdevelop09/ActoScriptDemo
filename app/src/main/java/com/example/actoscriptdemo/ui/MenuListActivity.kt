package com.example.actoscriptdemo.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.distinctUntilChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.actoscriptdemo.R
import com.example.actoscriptdemo.adapter.CategoryAdapter
import com.example.actoscriptdemo.adapter.MenuItemsAdapter
import com.example.actoscriptdemo.api.DETAILS
import com.example.actoscriptdemo.callBack.AddToCartClickListener
import com.example.actoscriptdemo.callBack.CategoryItemClickListener
import com.example.actoscriptdemo.databinding.ActivityMenuListBinding
import com.example.actoscriptdemo.model.SharePreference
import com.example.actoscriptdemo.viewModel.MyViewModel


class MenuListActivity : AppCompatActivity(), CategoryItemClickListener, AddToCartClickListener {

    private lateinit var binding: ActivityMenuListBinding
    private val TAG = "MainActivity"
    private var categoryItemList: ArrayList<DETAILS> = ArrayList()
    private val cartList: ArrayList<DETAILS> = ArrayList()
    private var sharePrefsList: ArrayList<DETAILS> = ArrayList()
    private var newSharePrefsList = MutableLiveData<ArrayList<DETAILS>>()
    private val firstCategoryItemList: ArrayList<DETAILS> = ArrayList()
    private var passItemList: DETAILS = DETAILS()
    private val itemList: ArrayList<DETAILS> = ArrayList()
    var stateList = ArrayList<String>()
    var stateIdList = ArrayList<String>()
    private lateinit var itemAdapter: MenuItemsAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var viewModel: MyViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        binding.progressBar.visibility=View.VISIBLE
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
        categoryAdapter = CategoryAdapter(categoryList, stateList.distinct(), stateIdList.distinct())
        binding.recyclerCategory.adapter = categoryAdapter
        categoryAdapter.setOnItemClickListener(this)
        categoryAdapter.selectedPosition = 0
        categoryAdapter.notifyDataSetChanged()
        binding.progressBar.visibility=View.GONE
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
        itemAdapter.notifyDataSetChanged()

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
        val apiCategoryItemList: ArrayList<DETAILS> = ArrayList()
        viewModel.fetchDataFromApi()
        viewModel.mLiveDataList.distinctUntilChanged().observe(this) { it ->
            Log.d(
                TAG,
                "onResponse__liveDatalist__: ${it.distinct().size}___"
            )

            if (it != null) {
                val detailList = it
                detailList.forEach {
                    apiCategoryItemList.add(it)
                    SharePreference.saveCategoryItemList(this,apiCategoryItemList)
                }
                categoryItemList = SharePreference.getCategoryItemList(this)
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
            else{
                binding.progressBar.visibility=View.GONE
            }
        }

    }

    private fun handleClickEvents() {
        binding.imgCart.setOnClickListener {

            val intent = Intent(this, CartDetailsActivity::class.java)
            intent.putParcelableArrayListExtra("liveData", categoryItemList)
            Log.d(TAG, "onItemFetched____check__pass: ${SharePreference.getExistingList(this)}")
            Log.d(TAG, "onItemFetched____check__pass: ${sharePrefsList.size}")
            if (sharePrefsList.size == 0) {
                intent.putParcelableArrayListExtra("cart", SharePreference.getExistingList(this))
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
        sharePrefsList = SharePreference.addNewList(this, cartList, "add")
    }

    override fun onResume() {
        super.onResume()

        if (::itemAdapter.isInitialized && ::categoryAdapter.isInitialized) {
            val existingList = SharePreference.getExistingList(this)
            val tempArrayList = SharePreference.getExistingList(this)
            sharePrefsList!!.forEach {
                if (tempArrayList != null && tempArrayList.isNotEmpty()) {
                    existingList.forEach { exist ->
                        if (it.FOODCATEGORYITEMID == exist.FOODCATEGORYITEMID!!) {
                            tempArrayList.remove(exist)
                        }
                    }
                    Log.d(TAG, "onResume___item_____if_____: ${sharePrefsList.size}")
                }
                else {
                    //recreate()
                    sharePrefsList.remove(it)
                    itemAdapter.removeItem(it)
                    categoryItemList.remove(it)
                    Log.d(
                        TAG,
                        "onResume___item_____else_____: ${sharePrefsList.size}___${it.ITEAMNAME}"
                    )

                }
            }
        }
    }


}
