package com.assignment.app.viewmodel

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.assignment.app.data.DeliveryRepository
import com.assignment.app.data.source.local.AppDatabase

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val activity: AppCompatActivity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val db =
            Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "delivery")
                .build()
        val repo = DeliveryRepository(db.deliveryDao())
        if (modelClass.isAssignableFrom(DeliveryListViewModel::class.java)) {
            return DeliveryListViewModel(db.deliveryDao(),repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}