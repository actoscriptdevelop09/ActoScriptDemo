package com.example.actoscriptdemo.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.actoscriptdemo.R
import com.example.actoscriptdemo.api.DETAILS
import com.example.actoscriptdemo.callBack.AddToCartClickListener
import com.example.actoscriptdemo.model.Constant


class MenuItemsAdapter(private val mList: ArrayList<DETAILS>) :
    RecyclerView.Adapter<MenuItemsAdapter.ViewHolder>() {
 //   private var isButtonClicked = false

    lateinit var context: Context
    lateinit var mClickListener : AddToCartClickListener
    fun setOnCartClickListener(clickListener: AddToCartClickListener){
        this.mClickListener = clickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]
        //  holder.imageView.setImageResource(itemsViewModel.image)
        if (itemsViewModel.ITEAMNAME != null && itemsViewModel.ITEAMNAME!!.isNotEmpty()) {
            holder.tvItemName.text = itemsViewModel.ITEAMNAME
        }
        holder.tvPrice.text = itemsViewModel.PRICE

        Glide.with(context)
            .load(itemsViewModel.IMAGEURL)
            .into(holder.itemImage)

        //cart click
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isButtonClicked = sharedPreferences.getBoolean("isButtonClicked_${itemsViewModel.FOODCATEGORYITEMID}", false)


        if (!isButtonClicked) {
            Log.d("TAG", "updateButtonState: clickeddd1")
            holder.addToCart.setBackgroundResource(R.drawable.bf_unselect_item)
            holder.tvAddCart.setTextColor(context.resources.getColor(R.color.black))
            val uri = "@drawable/baseline_shopping_bag_24"
            val res: Drawable = context.resources.getDrawable(
                context.resources.getIdentifier(
                    uri,
                    null,
                    context.packageName
                )
            )
            holder.imgCart.setImageDrawable(res)

        }
        else {
                Log.d("TAG", "updateButtonState: clickeddd2 ")
                holder.addToCart.setBackgroundResource(R.drawable.bg_login_button)
                holder.tvAddCart.setTextColor(context.resources.getColor(R.color.white))
                val uri = "@drawable/baseline_shopping_bag_24_white"
                val res: Drawable = context.resources.getDrawable(
                    context.resources.getIdentifier(
                        uri,
                        null,
                        context.packageName
                    )
                )
                holder.imgCart.setImageDrawable(res)
                Log.d("TAG", "onBindViewHolder___getItemUpdate__1: ${ holder.tvCount.text.toString()}")
            }
        holder.addToCart.setOnClickListener {

            val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val isButtonClicked = sharedPreferences.getBoolean("isButtonClicked_${itemsViewModel.FOODCATEGORYITEMID}", false)

            if (isButtonClicked) {
                // Button was clicked, remove its state
                editor.remove("isButtonClicked_${itemsViewModel.FOODCATEGORYITEMID}")
            } else {
                // Button was not clicked, store its state as clicked
                editor.putBoolean("isButtonClicked_${itemsViewModel.FOODCATEGORYITEMID}", true)
            }
            editor.apply()

            Log.d("TAG", "updateButtonState___checkkk+=: ${isButtonClicked}___${Constant.getButtonClick(context,"check")}")
            updateButtonState( holder.addToCart,holder.tvAddCart,holder.tvCount,holder.imgCart,position,itemsViewModel.FOODCATEGORYITEMID)
        }

        // +/- btn click
        var finalPrice : Int = itemsViewModel.PRICE?.toInt()!!
        var quantity = itemsViewModel.QUANTITY?.toInt()!!
        var getSharePrefs = Constant.getItem(context,"count_${mList[position].FOODCATEGORYITEMID}")
        if (getSharePrefs == "" || getSharePrefs == null){
            holder.tvCount.text = quantity.toString()
        }
        else{
            Log.d("TAG", "onBindViewHolder___check:" +
                    " ${mList[position].FOODCATEGORYITEMID}____" +
                    "${Constant.getItem(context,"count_${mList[position].FOODCATEGORYITEMID}")}___" +
                    "$getSharePrefs")

            if(getSharePrefs == Constant.getItem(context,"count_${mList[position].FOODCATEGORYITEMID}")){
                holder.tvCount.text = getSharePrefs
            }

        }
        holder.imgPlus.setOnClickListener {
            val price = itemsViewModel.PRICE?.toInt()!!

            quantity = if (getSharePrefs == "" || getSharePrefs == null){
                quantity!! + 1
            } else{
                holder.tvCount.text.toString().toInt() + 1
            }
            finalPrice += price
            SaveItemView(position, itemView = holder.imgPlus)
            saveQuantity(holder.tvCount,quantity.toString(),mList[position].FOODCATEGORYITEMID,finalPrice)
        }
        holder.imgMinus.setOnClickListener {
            var price = itemsViewModel.PRICE?.toInt()!!

            Log.d("TAG", "onBindViewHolder__quantity__:${quantity}____  ${holder.tvCount.text.toString().toInt() - 1}____ ${getSharePrefs}")

            if (holder.tvCount.text.toString().toInt() > 1){
                quantity = if (getSharePrefs == "" || getSharePrefs == null){
                     quantity -1
                } else{
                    holder.tvCount.text.toString().toInt() - 1
                }
                finalPrice -= price

                SaveItemView(position, itemView = holder.imgMinus)
                saveQuantity(holder.tvCount,quantity.toString(),mList[position].FOODCATEGORYITEMID,finalPrice)
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imgItem)
        val tvItemName: TextView = itemView.findViewById(R.id.txtItemName)
        val tvAddCart: TextView = itemView.findViewById(R.id.txtAddToCart)
        val addToCart: LinearLayout = itemView.findViewById(R.id.linearAddToCart)
        val imgCart: ImageView = itemView.findViewById(R.id.imgCart)
        val tvPrice: TextView = itemView.findViewById(R.id.txtPrice)
        val tvCount: TextView = itemView.findViewById(R.id.tvCount)
        val imgPlus: ImageView = itemView.findViewById(R.id.imgPlus)
        val imgMinus: ImageView = itemView.findViewById(R.id.imgMinus)
        val itemImage: ImageView = itemView.findViewById(R.id.imgItem)
    }

    private fun saveQuantity(
        textView: TextView,
        quantity: String,
        foodcategoryitemid: String?,
        finalPrice: Int
    ) {
        Log.d("TAG", "saveQuantity: $finalPrice")
       Constant.saveItem(context,"count_${foodcategoryitemid}",quantity)
       textView.text = Constant.getItem(context,"count_${foodcategoryitemid}")
       Constant.saveItemPrice(context,"price_${foodcategoryitemid}", finalPrice)
    }

    private fun SaveItemView(position: Int, itemView: View){
        val sharedPreferences = itemView.context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("selectedPosition", position)
        editor.apply()
    }

    fun updateButtonState(
        addToCart: LinearLayout,
        tvAddCart: TextView,
        tvCount: TextView,
        imgCart: ImageView,
        position: Int,
        foodcategoryitemid: String?
    ) {

        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isButtonClicked = sharedPreferences.getBoolean("isButtonClicked_${foodcategoryitemid}", false)

        if (!isButtonClicked) {
            Log.d("TAG", "updateButtonState: btnUnClick --> $isButtonClicked")
            Constant.saveButtonClick(context,"check_${foodcategoryitemid}",!isButtonClicked)
            addToCart.setBackgroundResource(R.drawable.bf_unselect_item)
            tvAddCart.setTextColor(context.resources.getColor(R.color.black))
                val uri = "@drawable/baseline_shopping_bag_24"
            val res: Drawable = context.resources.getDrawable(
                context.resources.getIdentifier(
                    uri,
                    null,
                    context.packageName
                )
            )
            imgCart.setImageDrawable(res)
            mClickListener.onItemFetched(mList[position],position,tvCount.text.toString(),mList[position].FOODCATEGORYITEMID,false )

        }
        else {
            Log.d("TAG", "updateButtonState: btnclick --> $isButtonClicked")
            Constant.saveButtonClick(context,"check_${foodcategoryitemid}",false)
            addToCart.setBackgroundResource(R.drawable.bg_login_button)
            tvAddCart.setTextColor(context.resources.getColor(R.color.white))
            val uri = "@drawable/baseline_shopping_bag_24_white"
            val res: Drawable = context.resources.getDrawable(
                context.resources.getIdentifier(
                    uri,
                    null,
                    context.packageName
                )
            )
            imgCart.setImageDrawable(res)
            Log.d("TAG", "onBindViewHolder___getItemUpdate__1: ${ tvCount.text.toString()}")
            mClickListener.onItemFetched(mList[position],position,tvCount.text.toString(),mList[position].FOODCATEGORYITEMID,true )
        }
    }

    fun removeItem(item: DETAILS) {
        val position = mList.indexOf(item)
        if (position != -1) {
            mList.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }
    }

}
