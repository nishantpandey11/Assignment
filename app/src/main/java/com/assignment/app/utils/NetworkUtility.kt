package com.assignment.app.utils

import android.content.Context
import android.net.ConnectivityManager


class NetworkUtility() {
    companion object {
        fun isInternetAvailable(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            cm.activeNetworkInfo.also {
                return it != null && it.isConnected
            }
        }
    }


}