package com.assignment.app.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.assignment.app.data.model.Delivery

@Database(entities = [Delivery::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deliveryDao(): DeliveryDao
}