package com.assignment.app.data.source.local

import androidx.paging.DataSource
import androidx.room.*
import com.assignment.app.data.model.Delivery

@Dao
interface DeliveryDao {
    @get:Query("SELECT * FROM Delivery")
    val all: List<Delivery>

    @Query("SELECT * FROM Delivery")
    fun allDeliveries(): DataSource.Factory<Int,Delivery>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(delivery: List<Delivery>)

    @Query("Delete from Delivery")
    fun deleteAll()

    @Update
    fun updateDelivery(delivery: Delivery?)

}