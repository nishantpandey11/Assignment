package com.assignment.app.utils

import android.R
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}

@BindingAdapter("picture")
fun loadImage(imageView: ImageView, imageURL: String?) {
    Glide.with(imageView.context)
        .load(imageURL)
        .placeholder(R.drawable.ic_menu_report_image)
        .error(R.drawable.ic_menu_report_image)
        .into(imageView)
}
