package com.example.yocipe

import android.app.Application
import com.example.yocipe.data.AppContainer
import com.example.yocipe.data.AppContainerImpl

class YocipeApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}