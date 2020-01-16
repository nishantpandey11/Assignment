package com.assignment.app.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sender(
    val phone: String,
    val name: String,
    val email: String
) : Parcelable {
    override fun toString(): String {
        return "Sender(phone='$phone', name='$name', email='$email')"
    }
}