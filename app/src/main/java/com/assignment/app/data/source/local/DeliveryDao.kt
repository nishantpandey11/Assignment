package com.assignment.app.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.assignment.app.data.model.Delivery
import io.reactivex.Single

@Dao
interface DeliveryDao {
    @get:Query("SELECT * FROM Delivery")
    val all: List<Delivery>

   // @Query("SELECT * FROM Delivery limit :limit offset :offset")
   // fun getDeliveries(limit:Int, offset:Int): Single<List<Delivery>>

    @Insert
    fun insertAll(vararg delivery: Delivery)

    @Query("Delete from Delivery")
    fun deleteAll()

    @Query("update Delivery set isFavorite=:isFav where id=:id ")
    fun updateDelivery(id:String,isFav:Boolean)

    @Update
    fun updateDelivery(delivery: Delivery)
}