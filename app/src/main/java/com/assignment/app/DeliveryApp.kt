package com.assignment.app

import android.app.Application
import com.assignment.app.di.component.AppComponent
import com.assignment.app.di.component.DaggerAppComponent


class DeliveryApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().application(this).build()
    }

}