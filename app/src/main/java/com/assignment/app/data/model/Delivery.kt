package com.assignment.app.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.assignment.app.utils.CURRENCY
import com.assignment.app.utils.DECIMAL_PATTERN
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
        val dfee = deliveryFee.removePrefix(CURRENCY).toFloat()
        val surcharge = surcharge.removePrefix(CURRENCY).toFloat()

        val df = DecimalFormat(DECIMAL_PATTERN)
        df.roundingMode = RoundingMode.CEILING

        //Log.e("==>price", (dfee + surcharge).toString())
        return df.format(dfee + surcharge);
    }

    override fun toString(): String {
        return "Delivery(id='$id', remarks='$remarks', pickupTime='$pickupTime', goodsPicture='$goodsPicture', deliveryFee='$deliveryFee', surcharge='$surcharge', route=$route, sender=$sender, favorite=$isFavorite)"
    }
}
