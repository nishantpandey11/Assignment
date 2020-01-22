package com.assignment.app.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sender(
    val phone: String,
    val name: String,
    val email: String
) : Parcelable