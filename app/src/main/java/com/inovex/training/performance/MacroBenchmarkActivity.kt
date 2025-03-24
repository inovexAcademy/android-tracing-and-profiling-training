// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License: MIT

package com.inovex.training.performance

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.inovex.fib.fibStd

class MacroBenchmarkActivity : Activity() {
    companion object {
        private val TAG = MacroBenchmarkActivity::class.simpleName
    }

    private lateinit var textViewCounter: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)

        textViewCounter = findViewById(R.id.textViewCounter)

        val fib = fibStd(0)

        textViewCounter.text = "$fib"

        Log.d(TAG, "onCreate() finished")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() finished")
    }
}
