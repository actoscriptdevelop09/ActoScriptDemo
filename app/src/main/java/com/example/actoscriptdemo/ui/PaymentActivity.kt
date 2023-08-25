package com.example.actoscriptdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.actoscriptdemo.R
import com.example.actoscriptdemo.api.DETAILS
import com.example.actoscriptdemo.databinding.ActivityPaymentBinding
import com.example.actoscriptdemo.model.Constant
import com.example.actoscriptdemo.model.PostData
import com.example.actoscriptdemo.socket.SocketViewModel
import com.example.actoscriptdemo.socket.WebSocketListenerConst
import com.example.actoscriptdemo.viewModel.MyViewModel
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.net.URISyntaxException

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding
    var totalPrice: String? = null
    var checkPaymentType: String? = null
    var checkCashPaymentType: String? = null
    var payAmount: String? = null
    var changePayment : Int = 0
    private val TAG = "PaymentActivity"
    var itemList: ArrayList<DETAILS> = ArrayList()
    private lateinit var viewModel: MyViewModel

    private lateinit var socket: Socket


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        socketConnection()
        getSharePrefsData()
        handleClickEvents()
    }

    private fun getSharePrefsData() {

        val receivedIntent = intent
        val receivedBundle = receivedIntent.extras
        if (receivedBundle != null) {
            totalPrice = receivedBundle.getString("price")
            itemList = receivedBundle.getParcelableArrayList<DETAILS>("list")!!
            Log.d("TAG", "handleClickEvents___price__payment: ${totalPrice}___${itemList?.size}")
            binding.tvTotalPayment.text = totalPrice.toString()
            binding.tvFullPayment.text = totalPrice.toString()
        }

    }

    private fun handleClickEvents() {
        binding.linearOnlinePayment.setOnClickListener {
            checkPaymentType = "Online"
            binding.imgOnline.setImageResource(R.drawable.baseline_radio_button_checked_24)
            binding.imgCash.setImageResource(R.drawable.baseline_radio_button_unchecked_24)
            binding.imgQrCode.visibility = View.VISIBLE
            binding.linearPaymentOptions.visibility = View.GONE
        }
        binding.linearCODPayments.setOnClickListener {
            checkPaymentType = "Cash"
            binding.imgCash.setImageResource(R.drawable.baseline_radio_button_checked_24)
            binding.imgOnline.setImageResource(R.drawable.baseline_radio_button_unchecked_24)
            binding.imgQrCode.visibility = View.GONE
            binding.linearPaymentOptions.visibility = View.VISIBLE

        }
        binding.btnPaymentDone.setOnClickListener {

            if (binding.etChangePayment.text.isEmpty()) {
                checkCashPaymentType = "Full"
                payAmount = totalPrice
                changePayment = 0
            } else {
                checkCashPaymentType = "Custom"
                payAmount = binding.etChangePayment.text.toString()
                changePayment =
                    binding.etChangePayment.text.toString().toInt() - totalPrice!!.toInt()
            }

            val postData = PostData(
                OPER = "Add",
                PAYMENTTYPE = checkPaymentType!!,
                CASHTYPE = checkCashPaymentType!!,
                ORDERFLAG = "OrderConfirm",
                BILLAMOUNT = totalPrice!!,
                PAYAMOUNT = payAmount!!,
                PENDINGAMOUNT = changePayment.toString(),
                Food_Categorydetails = itemList
            )
            viewModel.passItemListToConfirmOrder(postData)
        }
    }

    private fun socketConnection() {
        // Create the socket instance
        try {
            val options = IO.Options.builder().build()
            socket = IO.socket(Constant.socketUrl, options)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            Log.d("TAG__Socket", "socketConnection__catch : ${e.reason}")
        }

        socket.on(Socket.EVENT_CONNECT) {
            // Handle socket connection success
            Log.d("TAG__Socket", "socketConnection__c: ${socket.connected()}")
            if (socket.connected()){
                socket.emit("SendMessage", "hello socket  im krishna");
            }
        }

        socket.on("received_message") { args ->
            val data = args[0] as String
            // Handle received message
            Log.d("TAG__Socket", "socketConnection__msg: $data")
        }

        socket.connect()
    }


    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
    }

}