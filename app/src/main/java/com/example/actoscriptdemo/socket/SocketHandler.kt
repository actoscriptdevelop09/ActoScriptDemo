package com.example.actoscriptdemo.socket
import android.util.Log
import com.example.actoscriptdemo.model.SharePreference
import io.socket.client.IO;
import java.net.URISyntaxException

object SocketHandler {

    private lateinit var socket: io.socket.client.Socket

    fun connectWithSocket(){
        try {
            val options = IO.Options.builder().build()
            socket = IO.socket(SharePreference.socketUrl, options)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            Log.d("TAG__Socket", "socketConnection__catch : ${e.reason}")
        }

        socket.on(io.socket.client.Socket.EVENT_CONNECT) {
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

}