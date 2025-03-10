// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License: MIT

package com.inovex.training.performance

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import java.util.Timer
import java.util.TimerTask

class CPULeakActivity : Activity() {
    companion object {
        private val TAG = CPULeakActivity::class.simpleName
    }

    private lateinit var timer: Timer
    private var counter = 0L
    private lateinit var textViewCounter: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)

        textViewCounter = findViewById(R.id.textViewCounter)

        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                updateCounterBackground()
            }
        }, 0, 100)

        Log.d(TAG, "onCreate() finished")
    }

    fun updateCounterBackground() {
        // NOTE: calculation is done on the timer thread, not on UI thread!
        val acc = fibFast(4200)
        counter += acc

        runOnUiThread {
            // Update
            textViewCounter.text = "$counter"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        Log.d(TAG, "onDestroy() finished")
    }
}