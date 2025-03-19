// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License-Identifier: MIT

package com.inovex.microbenchmark

import androidx.benchmark.BlackHole
import androidx.benchmark.ExperimentalBlackHoleApi
import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.inovex.fib.fibCaching
import com.inovex.fib.fibFast
import com.inovex.fib.fibStd
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FibBenchmark {
    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @OptIn(ExperimentalBlackHoleApi::class)
    @Test
    fun fibStd() {
        benchmarkRule.measureRepeated {
            for (i in 0..1024) {
                // Using 42 here takes too long!
                // Since it's not the same value as below, you cannot compare the timing with the
                // other test functions below.
                BlackHole.consume(fibStd(21))
            }
        }
    }

    @OptIn(ExperimentalBlackHoleApi::class)
    @Test
    fun fibCaching() {
        benchmarkRule.measureRepeated {
            for (i in 0..1024) {
                BlackHole.consume(fibCaching(42))
            }
        }
    }

    @OptIn(ExperimentalBlackHoleApi::class)
    @Test
    fun fibFast() {
        benchmarkRule.measureRepeated {
            for (i in 0..1024) {
                BlackHole.consume(fibFast(42))
            }
        }
    }
}