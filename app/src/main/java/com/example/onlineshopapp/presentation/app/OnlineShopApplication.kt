package com.example.onlineshopapp.presentation.app

import android.app.Application
import com.example.onlineshopapp.di.DaggerApplicationComponent

class OnlineShopApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}