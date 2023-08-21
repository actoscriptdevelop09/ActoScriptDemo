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
import com.example.actoscriptdemo.R
import com.example.actoscriptdemo.api.DETAILS
import com.example.actoscriptdemo.model.Constant

class CartDetailsAdapter(private val mList: List<DETAILS>) :
    RecyclerView.Adapter<CartDetailsAdapter.ViewHolder>() {
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart_details, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]

        val isButtonClicked = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            .getBoolean("isButtonClicked_${itemsViewModel.FOODCATEGORYITEMID}", false)
        Log.d("TAG", "getCartList__check___innn: ${isButtonClicked}")

        if (isButtonClicked){
            holder.cardItemView.visibility = View.VISIBLE
            holder.tvItemName.text = itemsViewModel.ITEAMNAME
            holder.tvItemPrice.text = itemsViewModel.PRICE

            var quantity = itemsViewModel.QUANTITY?.toInt()!!
            Log.d("TAG", "onBindViewHolder__quantity: $quantity")
            holder.tvQuantity.text = quantity.toString()

            var getSharePrefs = Constant.getItem(context,"count_${mList[position].FOODCATEGORYITEMID}")
            if (getSharePrefs == "" || getSharePrefs == null){
                holder.tvQuantity.text = quantity.toString()
            }
            else{
                Log.d("TAG", "onBindViewHolder___check:" +
                        " ${mList[position].FOODCATEGORYITEMID}____" +
                        "${Constant.getItem(context,"count_${mList[position].FOODCATEGORYITEMID}")}___" +
                        "$getSharePrefs")

                if(getSharePrefs == Constant.getItem(context,"count_${mList[position].FOODCATEGORYITEMID}")){
                    holder.tvQuantity.text = getSharePrefs
                }

            }
            holder.imgPlus.setOnClickListener {
                quantity = if (getSharePrefs == "" || getSharePrefs == null){
                    quantity!! + 1
                } else{
                    holder.tvQuantity.text.toString().toInt() + 1
                }
                saveQuantity(holder.tvQuantity,quantity.toString(),mList[position].FOODCATEGORYITEMID)
            }
            holder.imgMinus.setOnClickListener {
                if (holder.tvQuantity.text.toString().toInt() > 1){
                    quantity = if (getSharePrefs == "" || getSharePrefs == null){
                        quantity -1
                    } else{
                        holder.tvQuantity.text.toString().toInt() - 1
                    }
                    saveQuantity(holder.tvQuantity,quantity.toString(),mList[position].FOODCATEGORYITEMID)
                }
            }

        }
        else{
            holder.cardItemView.visibility = View.GONE
        }


    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvItemName: TextView = itemView.findViewById(R.id.tvItemName)
        val cardItemView: CardView = itemView.findViewById(R.id.cardItemView)
        val tvItemPrice: TextView = itemView.findViewById(R.id.tvTotalPrice)
        val tvQuantity: TextView = itemView.findViewById(R.id.tvCount)
        val imgPlus: ImageView = itemView.findViewById(R.id.imgPlus)
        val imgMinus: ImageView = itemView.findViewById(R.id.imgMinus)
    }

    private fun saveQuantity(textView: TextView, quantity: String, foodcategoryitemid: String?) {
        Constant.saveItem(context,"count_${foodcategoryitemid}",quantity)
        textView.text = Constant.getItem(context,"count_${foodcategoryitemid}")
        Log.d("TAG", "check__update__go: ${Constant.getItem(context, "count_${foodcategoryitemid}")}")
    }
}