// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH

package com.inovex.training.performance

import android.app.Activity
import android.os.Bundle
import android.os.Trace
import android.util.Log
import android.widget.TextView
import java.util.Timer
import java.util.TimerTask

class MainActivity : Activity() {
    companion object {
        private val TAG = MainActivity::class.qualifiedName

        init {
            System.loadLibrary("native")
        }
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
                runOnUiThread {
                    updateCounter()
                }
            }
        }, 0, 100)

        Log.d(TAG, "from native " + getSomeNumberFromNative())
    }

    fun updateCounter() {
        try {
            Trace.beginSection("MainActivity-TimerTask")
            textViewCounter.text = counter.toString()

            counter += getAccFromNative()
        } finally {
            Trace.endSection()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    private external fun getSomeNumberFromNative(): Int

    private external fun getAccFromNative(): Int

}
