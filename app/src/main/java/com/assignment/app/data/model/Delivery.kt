package com.assignment.app.data.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.assignment.app.utils.CURRENCY
import com.assignment.app.utils.DECIMAL_PATTERN
import kotlinx.android.parcel.Parcelize
import java.math.RoundingMode
import java.text.DecimalFormat

@Parcelize
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
) : Parcelable {

    fun getPrice(): String {
        val dfee = deliveryFee.removePrefix(CURRENCY).toFloat()
        val surcharge = surcharge.removePrefix(CURRENCY).toFloat()

        val df = DecimalFormat(DECIMAL_PATTERN)
        df.roundingMode = RoundingMode.CEILING

        return df.format(dfee + surcharge)
    }

}
