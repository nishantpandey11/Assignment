package com.assignment.app.service.model

import android.util.Log
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.RoundingMode
import java.text.DecimalFormat

@Entity
data class Delivery(
    @field:PrimaryKey
    val id: String,
    val remarks: String,
    val pickupTime: String,
    val goodsPicture: String,
    val deliveryFee: String,
    val surcharge: String,
    @Embedded
    val route: Route,
    @Embedded
    val sender: Sender,
    var isFavorite: Boolean
) {

    fun getPrice(): String {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        val dfee = deliveryFee.removePrefix("$").toFloat()
        val surcharge = surcharge.removePrefix("$").toFloat()
        Log.e("==>price", (dfee + surcharge).toString())
        return df.format(dfee + surcharge);

    }

    override fun toString(): String {
        return "Delivery(id='$id', remarks='$remarks', pickupTime='$pickupTime', goodsPicture='$goodsPicture', deliveryFee='$deliveryFee', surcharge='$surcharge', route=$route, sender=$sender, favorite=$isFavorite)"
    }
}
