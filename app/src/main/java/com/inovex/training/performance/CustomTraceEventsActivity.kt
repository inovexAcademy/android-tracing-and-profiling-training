// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License: MIT

package com.inovex.training.performance

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import java.util.Timer
import java.util.TimerTask

class CustomTraceEventsActivity : Activity() {
    companion object {
        private val TAG = CustomTraceEventsActivity::class.simpleName
    }

    private lateinit var timer: Timer
    private lateinit var textViewCounter: TextView
    private var counter = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)

        textViewCounter = findViewById(R.id.textViewCounter)

        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                updateCounter()
            }
        }, 0, 100)

        Log.d(TAG, "onCreate() finished")
    }

    fun updateCounter() {
        counter += getAccFromNative()

        runOnUiThread {
            textViewCounter.text = "$counter"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        Log.d(TAG, "onDestroy() finished")
    }

    private external fun getAccFromNative(): Int
}
