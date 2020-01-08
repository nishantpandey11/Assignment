package com.assignment.app.view.ui.deliverylist

import androidx.lifecycle.MutableLiveData
import com.assignment.app.base.BaseViewModel
import com.assignment.app.service.model.Delivery

class DeliveryViewModel: BaseViewModel() {
    private val deliveryFrom = MutableLiveData<String>()
    private val deliveryTo = MutableLiveData<String>()
    private val deliveryFee = MutableLiveData<String>()
    private val goodsPic = MutableLiveData<String>()

    fun bind(delivery: Delivery){
        deliveryFrom.value = delivery.route.start
        deliveryTo.value = delivery.route.end
        deliveryFee.value = delivery.deliveryFee
        goodsPic.value = delivery.goodsPicture

    }
    fun getDeliveryFrom():MutableLiveData<String>{
        return deliveryFrom
    }
    fun getDeliveryTo():MutableLiveData<String>{
        return deliveryTo
    }
    fun getDeliveryFee():MutableLiveData<String>{
        return deliveryFee
    }
    fun getGoodsPic():MutableLiveData<String>{
        return goodsPic
    }

}