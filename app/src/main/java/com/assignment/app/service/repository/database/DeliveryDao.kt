package com.assignment.app.service.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.assignment.app.service.model.Delivery

@Dao
interface DeliveryDao {
    @get:Query("SELECT * FROM Delivery")
    val all: List<Delivery>

    @Insert
    fun insertAll(vararg delivery: Delivery)

    @Query("Delete from Delivery")
    fun deleteAll()

    @Query("update Delivery set isFavorite=:isFav where id=:id ")
    fun updateDelivery(id:String,isFav:Boolean)

    @Update
    fun updateDelivery(delivery: Delivery)
}