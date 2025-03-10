// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License: MIT

package com.inovex.training.performance

import android.app.Activity
import android.os.Bundle
import android.os.Trace
import android.util.Log
import android.widget.TextView

class MacroBenchmarkActivity : Activity() {
    companion object {
        private val TAG = MacroBenchmarkActivity::class.simpleName
    }

    private lateinit var textViewCounter: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)

        textViewCounter = findViewById(R.id.textViewCounter)

        val fib = try {
            Trace.beginSection("fibStd")
            fibStd(23 + 10)
        } finally {
            Trace.endSection()
        }
        textViewCounter.text = "$fib"


        Log.d(TAG, "onCreate() finished")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() finished")
    }
}
