// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License: MIT

package com.inovex.training.performance

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class CoroutineTraceActivity : Activity() {
    companion object {
        private val TAG = CPULeakActivity::class.simpleName
    }

    private lateinit var timer: Timer
    private var counter = 0L
    private lateinit var textViewCounter: TextView
    private val scope = CoroutineScope(Job() + Dispatchers.Main)

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

    private fun heavyFunction(): Int {
        val startTimeInMs = System.currentTimeMillis()
        var acc = 0
        // Compute for 20 milliseconds
        while (System.currentTimeMillis() < startTimeInMs + 20) {
            acc += 1
        }
        return acc
    }

    private fun startHeavyTask(): Deferred<Int> {
        return scope.async { heavyFunction() }
    }

    fun updateCounterBackground() {
        val job1 = startHeavyTask()
        val job2 = startHeavyTask()
        val job3 = startHeavyTask()

        scope.launch(Dispatchers.Main) {
            counter += job1.await() + job2.await() + job3.await()
            textViewCounter.text = "$counter"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        Log.d(TAG, "onDestroy() finished")
    }
}