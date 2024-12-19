package com.example.testingapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast

class NetworkChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        val isConnected = networkInfo != null && networkInfo.isConnected

        // Update your UI or perform actions based on the connectivity status
        if (isConnected) {
            // Internet connected
            showConnectedMessage(context)
        } else {
            // No internet connection
            showDisconnectedMessage(context)
        }

    }

    private fun showConnectedMessage(context: Context) {
        // Implement your logic to show a message or perform actions when connected
        // This example just logs a message
        Toast.makeText(context, "Connected to internet", Toast.LENGTH_SHORT).show()
    }

    private fun showDisconnectedMessage(context: Context) {
        // Implement your logic to show a message or perform actions when disconnected
        // This example just logs a message
        Toast.makeText(context, "Disconnected from internet", Toast.LENGTH_SHORT).show()

    }
}