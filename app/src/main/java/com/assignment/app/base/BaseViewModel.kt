package com.assignment.app.base

import androidx.lifecycle.ViewModel
import com.assignment.app.di.component.DaggerViewModelInjector
import com.assignment.app.di.component.ViewModelInjector
import com.assignment.app.di.module.NetworkModule
import com.assignment.app.viewmodel.DeliveryListViewModel

abstract class BaseViewModel: ViewModel() {

    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
             is DeliveryListViewModel -> injector.inject(this)
        }
    }
}