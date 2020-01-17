package com.assignment.app.data.source.local

import androidx.paging.DataSource
import androidx.room.*
import com.assignment.app.data.model.Delivery
import io.reactivex.Single

@Dao
interface DeliveryDao {
    @get:Query("SELECT * FROM Delivery")
    val all: List<Delivery>

    @Query("SELECT * FROM Delivery")
    fun allDeliveries(): DataSource.Factory<Int,Delivery>

   // @Query("SELECT * FROM Delivery limit :limit offset :offset")
   // fun getDeliveries(limit:Int, offset:Int): Single<List<Delivery>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(delivery: List<Delivery>)

    @Query("Delete from Delivery")
    fun deleteAll()

    @Query("update Delivery set isFavorite=:isFav where id=:id ")
    fun updateDelivery(id:String,isFav:Boolean)

    @Update
    fun updateDelivery(delivery: Delivery)
}