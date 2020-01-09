package com.assignment.app.view.callback

import com.assignment.app.service.model.Delivery

interface ItemClickCallback {
    fun onItemClick(delivery: Delivery?)
}