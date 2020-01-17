package com.assignment.app.viewmodel

import androidx.lifecycle.MutableLiveData
import com.assignment.app.base.BaseViewModel
import com.assignment.app.data.model.Delivery

class DeliveryViewModel : BaseViewModel() {
    private val delivery = MutableLiveData<Delivery>()

    fun bind(delivery: Delivery?) {
        this.delivery.value = delivery

    }

    fun getDelivery(): MutableLiveData<Delivery> {
        return delivery
    }

}