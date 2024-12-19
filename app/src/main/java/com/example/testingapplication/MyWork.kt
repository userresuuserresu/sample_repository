package com.example.testingapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.testingapplication.WorkManagerActivity.Companion.TASK_KEY

class MyWork(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    companion object {
        const val RECEIVE_TASK_KEY = "receive_message"
    }

    override fun doWork(): Result {
        try {
            // Get input tasks by the User or WorkManager
            //val data = inputData
            // Notification function
           // notificationShow("appbuilder", data.getString(TASK_KEY))
            // Set a response when the Task is done
            val data1 = Data.Builder().putString(RECEIVE_TASK_KEY, "appbuilder Receive data Successfully and Thank you ").build()
            // Return the task status
            return Result.success(data1)

        }catch (e: Exception) {
            Log.d("TAG", "doWork exception: "+e.message)

            return Result.failure()
        }

    }

    // Create notification method
/*    private fun notificationShow(title: String, desc: String?) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("appbuilder", "appbuilder", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(applicationContext, "appbuilder")
            .setContentTitle(title)
            .setContentText(desc)
            .setSmallIcon(R.mipmap.ic_launcher)

        notificationManager.notify(1, builder.build())
    }*/
}
