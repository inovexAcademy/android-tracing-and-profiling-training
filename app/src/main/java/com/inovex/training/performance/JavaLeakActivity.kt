// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License: MIT

package com.inovex.training.performance

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import java.util.Timer
import java.util.TimerTask

class JavaLeakActivity : Activity() {
    companion object {
        private val TAG = JavaLeakActivity::class.simpleName
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
                updateCounter()
            }
        }, 0, 100)

        Log.d(TAG, "onCreate() finished")
    }

    fun updateCounter() {
        // Create 1024 integer objects every call
        val values = Array(1024) { i -> i * 2 }

        // Just use some value out of the array
        val acc = values[values.size / 2]

        counter += acc
        runOnUiThread {
            textViewCounter.text = "$counter"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        Log.d(TAG, "onDestroy() finished")
    }

    private external fun leakReference(o: Any)
}