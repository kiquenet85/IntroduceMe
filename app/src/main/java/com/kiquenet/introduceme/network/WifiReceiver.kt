package com.kiquenet.introduceme.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

/**
 * A local broadcast receiver which enable to get connection change events and the corresponding information.
 * @author n.diazgranados
 */
//http://trycatchworld.blogspot.com/2016/10/android-communicating-between.html
abstract class WifiReceiver : BroadcastReceiver() {

    companion object {
        private var connectedOnlineStatus: Boolean = false
    }

    /**
     * The Event produced by the Broadcast receiver.
     */
    inner class NetworkConnectionChange {
        var isOnline: Boolean = false
    }

    override fun onReceive(context: Context, intent: Intent) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        //should check null because in air plan mode it will be null
        val isOnline = netInfo != null && netInfo.isConnected

        val networkConnectionChange = NetworkConnectionChange()
        networkConnectionChange.isOnline = isOnline
        onConnectionEvent(networkConnectionChange)
    }

    abstract fun onConnectionEvent(networkConnectionChange: NetworkConnectionChange)
}
