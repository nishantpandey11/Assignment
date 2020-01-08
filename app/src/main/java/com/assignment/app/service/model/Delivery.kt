package com.assignment.app.service.model

import android.R
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


data class Delivery(
    val id: String,
    val remarks: String,
    val pickupTime: String,
    val goodsPicture: String,
    val deliveryFee: String,
    val surcharge: String,
    val route: Route,
    val sender: Sender
) {

    fun getPrice(): Int {
        return Integer.parseInt(deliveryFee.removePrefix("$")) + Integer.parseInt(
            surcharge.removePrefix(
                "$"
            )
        )

    }

    override fun toString(): String {
        return "Delivery(id='$id', remarks='$remarks', pickupTime='$pickupTime', goodsPicture='$goodsPicture', deliveryFee='$deliveryFee', surcharge='$surcharge', route=$route, sender=$sender)"
    }


    /*
     companion object {
        @BindingAdapter("app:picture")
        @JvmStatic
        fun loadImage(imageView: ImageView, imageURL: String?) {
            Glide.with(imageView.context)
                .setDefaultRequestOptions(
                    RequestOptions().circleCrop())
                .load(imageURL)
                .placeholder(R.drawable.ic_menu_report_image)
                .into(imageView)
        }
    }
     */
}
