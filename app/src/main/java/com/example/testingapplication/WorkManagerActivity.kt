package com.example.testingapplication

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest

class WorkManagerActivity : AppCompatActivity() {
     val TAG = "WorkManagerActivityy"
    companion object {
        const val TASK_KEY = "Input_TASK_KEY"
    }

    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_manager)

         val workInfoObserver = Observer<WorkInfo> { workInfo ->
            workInfo?.let {
                val state = workInfo.state
                val workId = workInfo.id
                // Handle the state of the observed work request
                when (state) {
                    WorkInfo.State.ENQUEUED -> {
                        Log.d(TAG, "Work request ${workInfo.tags} has been enqueued but not yet started")
                    }
                    WorkInfo.State.RUNNING -> {
                        Log.d(TAG, "Work request ${workInfo.tags} is currently running")
                    }
                    WorkInfo.State.SUCCEEDED -> {
                        Log.d(TAG, "Work request ${workInfo.tags} has completed successfully")
                    }
                    WorkInfo.State.FAILED -> {
                        Log.d(TAG, "Work request ${workInfo.tags} has failed")
                    }
                    WorkInfo.State.CANCELLED -> {
                        Log.d(TAG, "Work request ${workInfo.tags} has been cancelled")
                    }
                    WorkInfo.State.BLOCKED -> {
                        Log.d(TAG, "Work request ${workInfo.tags} is blocked and waiting for its constraints to be met")
                    }
                }
            }
        }


        textView = findViewById(R.id.textView)
        // Create input data for the worker
        val data = Data.Builder()
            .putString(TASK_KEY, "appbuilder this massage send by data!")
            .build()

        // Define constraints for the worker like when the device is idle, charging, or connected to a network
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()


        val oneTimeWorkRequest_1 = OneTimeWorkRequest.Builder(MyWork::class.java)
            .setInputData(data)
            .setConstraints(constraints)
            .addTag("task_1")
            .build()

        val oneTimeWorkRequest_2 = OneTimeWorkRequest.Builder(MyWork::class.java)
            .setInputData(data)
            .setConstraints(constraints)
            .addTag("task_2")
            .build()

        val oneTimeWorkRequest_3 = OneTimeWorkRequest.Builder(MyWork::class.java)
            .setInputData(data)
            .setConstraints(constraints)
            .addTag("task_3")
            .build()

        val oneTimeWorkRequest_4 = OneTimeWorkRequest.Builder(MyWork::class.java)
            .setInputData(data)
            .setInitialDelay(5, java.util.concurrent.TimeUnit.SECONDS)
            .setConstraints(constraints)
            .addTag("task_4")
            .build()

        val oneTimeWorkRequest_5 = OneTimeWorkRequest.Builder(MyWork::class.java)
            .setInputData(data)
            .setInitialDelay(5, java.util.concurrent.TimeUnit.SECONDS)
            .setConstraints(constraints)
            .addTag("task_5")
            .build()

        val workManager = WorkManager.getInstance(this)

        // Get the LiveData for each work request by their IDs
        val firstWorkInfoLiveData_1 = workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest_1.id)
        val firstWorkInfoLiveData_2 = workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest_2.id)
        val firstWorkInfoLiveData_3 = workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest_3.id)
        val firstWorkInfoLiveData_4 = workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest_4.id)
        val firstWorkInfoLiveData_5 = workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest_5.id)

        // Observe the LiveData for each work request to receive updates about their states
        firstWorkInfoLiveData_1.observe(this, workInfoObserver)
        firstWorkInfoLiveData_2.observe(this, workInfoObserver)
        firstWorkInfoLiveData_3.observe(this, workInfoObserver)
        firstWorkInfoLiveData_4.observe(this, workInfoObserver)
        firstWorkInfoLiveData_5.observe(this, workInfoObserver)

        // Set a click listener to enqueue the work request when the button is clicked
        findViewById<View>(R.id.btn_start).setOnClickListener {
            // WorkManager.getInstance().enqueue(listOneTimeWorkRequest)

            WorkManager.getInstance(this)
                // Candidates to run in parallel
                .beginWith(listOf(oneTimeWorkRequest_1, oneTimeWorkRequest_2, oneTimeWorkRequest_3))
                // Dependent work (only runs after all previous work in chain)
                .then(oneTimeWorkRequest_4)
                .then(oneTimeWorkRequest_5)
                // Call enqueue to kick things off
                .enqueue()
        }


    }
}