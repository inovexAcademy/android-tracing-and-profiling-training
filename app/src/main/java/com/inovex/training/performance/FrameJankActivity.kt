// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License: MIT

package com.inovex.training.performance

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.Choreographer
import android.widget.TextView
import androidx.compose.ui.util.trace

class FrameJankActivity : Activity(), Choreographer.FrameCallback {
    companion object {
        private val TAG = FrameJankActivity::class.simpleName
    }

    private var counter = 0L
    private lateinit var textViewCounter: TextView
    private lateinit var choreographer: Choreographer
    private var veryLongString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)

        choreographer = Choreographer.getInstance()
        choreographer.postFrameCallback(this)

        textViewCounter = findViewById(R.id.textViewCounter)

        for (i in 0..1024) veryLongString += "asdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaasdfasdf\n"

        Log.d(TAG, "onCreate() finished")
    }

    override fun onDestroy() {
        super.onDestroy()
        choreographer.removeFrameCallback(this)
        Log.d(TAG, "onDestroy() finished")
    }

    override fun doFrame(p0: Long) {
        trace("doFrame") {
            counter += 1

            if (counter % 10L == 1L) {
                trace("veryLongString") {
                    textViewCounter.text = veryLongString
                }
            } else {
                textViewCounter.text = "$counter"
            }
        }

        choreographer.postFrameCallback(this)
    }
}
