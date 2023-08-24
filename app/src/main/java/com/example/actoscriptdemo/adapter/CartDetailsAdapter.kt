package com.example.actoscriptdemo.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.actoscriptdemo.R
import com.example.actoscriptdemo.api.DETAILS
import com.example.actoscriptdemo.callBack.FilterableItemListener
import com.example.actoscriptdemo.model.Constant

class CartDetailsAdapter(private var mList: List<DETAILS>) :
    RecyclerView.Adapter<CartDetailsAdapter.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var itemListener: FilterableItemListener
    private var newList: MutableList<DETAILS> = mutableListOf()
    private var newNewList: MutableList<DETAILS> = mutableListOf()

    fun setItemListener(listener: FilterableItemListener) {
        this.itemListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart_details, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var isButtonClicked: Boolean = false
        if (position < mList.size) {
            isButtonClicked = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                .getBoolean("isButtonClicked_${mList[position].FOODCATEGORYITEMID}", false)
            Log.d("TAG", "getCartList__check___innn: ${isButtonClicked}")

        }

        if (isButtonClicked) {

            val itemsViewModel = mList[position]
            Log.d("TAG", "onBindViewHolder__checkForImage: $itemsViewModel")
            Glide.with(context)
                .load( itemsViewModel.IMAGEURL)
                .into(holder.itemImage)
            itemListener.onDataFetched(mList[position], position)
            holder.cardItemView.visibility = View.VISIBLE
            holder.tvItemName.text = itemsViewModel.ITEAMNAME
            holder.tvItemPrice.text = itemsViewModel.PRICE

            var finalPrice : Int? = Constant.getItemPrice(context, "price_${mList[position].FOODCATEGORYITEMID}")
            var getSharePrefsItemPrice = Constant.getItemPrice(context, "price_${mList[position].FOODCATEGORYITEMID}")
            Log.d("TAG", "onBindViewHolder___price: $finalPrice")
            if (getSharePrefsItemPrice !=  0) {
                finalPrice = getSharePrefsItemPrice!!
                holder.tvItemPrice.text = finalPrice.toString()
            } else {
                finalPrice = itemsViewModel.PRICE!!.toInt()
                holder.tvItemPrice.text = itemsViewModel.PRICE
            }
            Log.d("TAG", "onBindViewHolder___finalPrice: $finalPrice")

            var quantity = itemsViewModel.QUANTITY?.toInt()!!
            Log.d("TAG", "onBindViewHolder__quantity: $quantity")
            holder.tvQuantity.text = quantity.toString()
            var getSharePrefsItem =
                Constant.getItem(context, "count_${mList[position].FOODCATEGORYITEMID}")
            if (getSharePrefsItem == "" || getSharePrefsItem == null) {
                holder.tvQuantity.text = quantity.toString()
            } else {
                Log.d(
                    "TAG", "onBindViewHolder___check:" +
                            " ${mList[position].FOODCATEGORYITEMID}____" +
                            "${
                                Constant.getItem(
                                    context,
                                    "count_${mList[position].FOODCATEGORYITEMID}"
                                )
                            }___" +
                            "$getSharePrefsItem"
                )

                if (getSharePrefsItem == Constant.getItem(
                        context,
                        "count_${mList[position].FOODCATEGORYITEMID}"
                    )
                ) {
                    holder.tvQuantity.text = getSharePrefsItem
                }

            }
            holder.imgPlus.setOnClickListener {
                quantity = if (getSharePrefsItem == "" || getSharePrefsItem == null) {
                    quantity!! + 1
                } else {
                    holder.tvQuantity.text.toString().toInt() + 1
                }
                val price = itemsViewModel.PRICE!!.toInt()
                finalPrice += price
                Log.d("TAG", "onBindViewHolder___finalPrice_+: $finalPrice")

                saveQuantity(
                    holder.tvQuantity,
                    quantity.toString(),
                    mList[position].FOODCATEGORYITEMID,
                    holder.tvItemPrice,
                    finalPrice
                )

            }
            holder.imgMinus.setOnClickListener {
                if (holder.tvQuantity.text.toString().toInt() > 1) {
                    quantity = if (getSharePrefsItem == "" || getSharePrefsItem == null) {
                        quantity - 1
                    } else {
                        holder.tvQuantity.text.toString().toInt() - 1
                    }
                    val price = itemsViewModel.PRICE!!.toInt()
                    finalPrice -= price
                    Log.d("TAG", "onBindViewHolder___finalPrice_+: $finalPrice")
                    saveQuantity(
                        holder.tvQuantity,
                        quantity.toString(),
                        mList[position].FOODCATEGORYITEMID,
                        holder.tvItemPrice,
                        finalPrice
                    )
                }
            }

        } else {

            holder.cardItemView.visibility = View.GONE
        }
        //      }

    }

    override fun getItemCount(): Int {

        if (newList.isNotEmpty()) {
            newList.size
            newList.forEach {
                newNewList.add(it)
                Log.d("TAG", "getItemCount__if: ${newNewList.size}___-${it.ITEAMNAME}")
            }
            return newList.size
        } else {
            return mList.size
            Log.d("TAG", "getItemCount__else: ${mList.size}")
        }


    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvItemName: TextView = itemView.findViewById(R.id.tvItemName)
        val cardItemView: CardView = itemView.findViewById(R.id.cardItemView)
        val tvItemPrice: TextView = itemView.findViewById(R.id.tvTotalPrice)
        val tvQuantity: TextView = itemView.findViewById(R.id.tvCount)
        val imgPlus: ImageView = itemView.findViewById(R.id.imgPlus)
        val imgMinus: ImageView = itemView.findViewById(R.id.imgMinus)
        val itemImage: ImageView = itemView.findViewById(R.id.imgItem)

    }

    private fun saveQuantity(
        textView: TextView,
        quantity: String,
        foodcategoryitemid: String?,
        tvPrice : TextView,
        finalPrice: Int
    ) {
        Constant.saveItem(context, "count_${foodcategoryitemid}", quantity)
        textView.text = Constant.getItem(context, "count_${foodcategoryitemid}")
        Constant.saveItemPrice(context,"price_${foodcategoryitemid}", finalPrice)
        tvPrice.text = Constant.getItemPrice(context, "price_${foodcategoryitemid}").toString()

    }

    fun setFilterList(list: MutableList<DETAILS>) {
        Log.d("TAG", "setFilterList: ${list.size}")
        newList = list
        Log.d("TAG", "setFilterList: ${newList.size}")
        notifyDataSetChanged()
    }

    fun setCartList(){

    }

}