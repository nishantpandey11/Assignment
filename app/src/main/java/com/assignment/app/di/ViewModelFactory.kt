package com.assignment.app.di

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.assignment.app.service.repository.database.AppDatabase
import com.assignment.app.viewmodel.DeliveryListViewModel

class ViewModelFactory(private val activity: AppCompatActivity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeliveryListViewModel::class.java)) {
            val db =
                Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "delivery")
                    .build()
            @Suppress("UNCHECKED_CAST")
            return DeliveryListViewModel(db.deliveryDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}