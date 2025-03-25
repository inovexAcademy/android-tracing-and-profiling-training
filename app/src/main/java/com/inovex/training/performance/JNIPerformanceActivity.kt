// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License: MIT

package com.inovex.training.performance

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.compose.ui.util.trace
import dalvik.annotation.optimization.CriticalNative
import dalvik.annotation.optimization.FastNative
import java.util.Timer
import java.util.TimerTask

class JNIPerformanceActivity : Activity() {
    companion object {
        private val TAG = JNIPerformanceActivity::class.simpleName

        @CriticalNative
        @JvmStatic
        private external fun addCriticalNative(a: Int, b: Int): Int
    }

    private lateinit var timer: Timer
    private lateinit var textViewCounter: TextView
    private var counter = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)

        textViewCounter = findViewById(R.id.textViewCounter)

        // Simple test that the C++ implementation is correct
        assert(add(5, 3) == 8)
        assert(addFastNative(5, 3) == 8)
        assert(addCriticalNative(5, 3) == 8)

        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                updateCounter()
            }
        }, 0, 100)

        Log.d(TAG, "onCreate() finished")
    }

    fun updateCounter() {
        var value = 0

        trace("add") {
            value += add(21, 21)
        }
        trace("addFastNative") {
            value += addFastNative(21, 21)
        }
        trace("addCriticalNative") {
            value += addCriticalNative(21, 21)
        }


        counter += value
        runOnUiThread {
            textViewCounter.text = "$counter"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        Log.d(TAG, "onDestroy() finished")
    }

    private external fun add(a: Int, b: Int): Int

    @FastNative
    private external fun addFastNative(a: Int, b: Int): Int

}
