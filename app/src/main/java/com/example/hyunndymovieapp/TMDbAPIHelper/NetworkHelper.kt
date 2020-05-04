package com.example.hyunndymovieapp.TMDbAPIHelper

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkHelper(val context: Context) {

    fun checkNetworkState() : Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
    }
}