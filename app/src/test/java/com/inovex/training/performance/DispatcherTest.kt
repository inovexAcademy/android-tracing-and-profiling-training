// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License: MIT

package com.inovex.training.performance

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.time.measureTime

@OptIn(ExperimentalCoroutinesApi::class)
class DispatcherTest {
    private fun cpu() {
        calculateFactorial(20000)
    }

    private fun blocking() {
        Thread.sleep(1000)
    }

    private suspend fun suspending() {
        delay(1000)
    }

    private val dispatcher =
        Dispatchers.IO.limitedParallelism(1)
//    Dispatchers.IO
//    Dispatchers.IO.limitedParallelism(100)
//    Dispatchers.Default

    @Test
    fun testDispatcherPerformance(): Unit = runTest {
        measureTime {
            coroutineScope {
                repeat(100) {
                    launch(dispatcher) {
                        cpu()
//                blocking()
//                suspending()
                        println("$it: Done ${Thread.currentThread()}")
                    }
                }
            }
        }.let { println("Duration: $it") }
    }

    /**
     *                   CPU     Blocking    Suspending
     * 1 Thread
     * IO
     * IO (100 Threads)
     * Default
     */
}
