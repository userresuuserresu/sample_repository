package com.example.testingapplication

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
class ForegroundService : Service() {

    private val notificationChannelId = "foreground_service_channel"
    private val notificationId = 1

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannelId,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Channel for foreground service notifications"
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("ForegroundServiceType")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = createNotification()
        startForeground(notificationId, notification)
        // Start your long-running task here
        Thread {
            for (i in 0..100) {
                Log.d("ForegroundService", "Task progress: $i%")
                Thread.sleep(100)
            }
            stopForeground(true)
            stopSelf()
        }.start()
        return START_REDELIVER_INTENT
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotification(): Notification {
        val builder = Notification.Builder(this, notificationChannelId)
            .setContentTitle("Foreground Service")
            .setContentText("Long-running task in progress")
            .setSmallIcon(androidx.constraintlayout.widget.R.drawable.abc_vector_test) // Replace with your icon resource
            .setPriority(Notification.PRIORITY_LOW)
        return builder.build()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
