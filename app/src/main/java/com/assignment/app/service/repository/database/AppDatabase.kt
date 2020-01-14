package com.assignment.app.service.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.assignment.app.service.model.Delivery

@Database(entities = [Delivery::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deliveryDao(): DeliveryDao
}