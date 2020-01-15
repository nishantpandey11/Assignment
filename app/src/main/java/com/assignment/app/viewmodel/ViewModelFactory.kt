package com.assignment.app.viewmodel

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.assignment.app.data.DeliveryRepository
import com.assignment.app.data.source.local.AppDatabase

class ViewModelFactory(private val activity: AppCompatActivity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeliveryListViewModel::class.java)) {
            val db =
                Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "delivery")
                    .build()
            val repo = DeliveryRepository(db.deliveryDao())
            @Suppress("UNCHECKED_CAST")
            return DeliveryListViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}