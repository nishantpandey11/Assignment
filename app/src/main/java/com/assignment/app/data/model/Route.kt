package com.assignment.app.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Route(
    val start: String,
    val end: String
) : Parcelable {
    override fun toString(): String {
        return "Route(start='$start', end='$end')"
    }
}