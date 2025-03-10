// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License: MIT

package com.inovex.training.performance

import android.app.Application
import android.util.Log

// Use a Android Application class to load the native shared library "libnative.so".
// It's used in multiple class/activities.
class Application : Application() {
    companion object {
        private val TAG = Application::class.simpleName
    }

    init {
        System.loadLibrary("native")
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate() called")
    }
}
