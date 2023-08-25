package com.example.actoscriptdemo.model

import android.app.Application
import android.content.Context

class MyApp : Application() {

    companion object {
        private var context: Context? = null

        lateinit var instance: MyApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext

    }

    fun getAppContext(): Context? {
        return context
    }

}