package com.example.carapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CarApp : Application() {
    override fun onCreate() {
        appContext = this
        super.onCreate()
    }
    companion object{
        lateinit var appContext: Application
    }
}