package com.assignment.app.view.callback

import com.assignment.app.data.model.Delivery

interface ItemClickCallback {
    fun onItemClick(delivery: Delivery)
}