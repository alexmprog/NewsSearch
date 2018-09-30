package com.renovavision.newssearch.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.WindowManager
import javax.inject.Inject

class Utils @Inject constructor(private val context: Context) {

    @SuppressLint("MissingPermission")
    fun isConnectedToInternet(): Boolean {
        val connectivity = context.getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivity.allNetworkInfo
        info.let {
            for (i in it.indices)
                if (it[i].state == NetworkInfo.State.CONNECTED) {
                    return true
                }
        }
        return false
    }

    fun getWindowSize(): Point {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val defaultDisplay = windowManager.defaultDisplay
        val size = Point()
        defaultDisplay.getSize(size)
        return size
    }
}