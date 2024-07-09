package com.abhishek.pathak.kotlin.android.githubcompose.ui.feature.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class BroadcastReceiver(private val onConnectivityChange: (Boolean) -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val isConnected = isNetworkConnected(context)
        onConnectivityChange(isConnected)
    }
    private fun isNetworkConnected(context: Context):Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}