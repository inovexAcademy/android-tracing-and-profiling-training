// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License-Identifier: MIT
//
// The code and idea is based on
//     https://www.romainguy.dev/posts/2024/you-are-going-to-need-it/

package com.inovex.microbenchmark

import androidx.benchmark.BlackHole
import androidx.benchmark.ExperimentalBlackHoleApi
import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.pow

@RunWith(AndroidJUnit4::class)
class MathBenchmark {
    @get:Rule
    val benchmarkRule = BenchmarkRule()

    private val data = FloatArray(8_192) {
        it.toFloat() / 3f
    }

    // NOTE: This is optimized away, so the benchmark is invalid!
    @Test
    fun pow2ButInvalidBenchmark() {
        benchmarkRule.measureRepeated {
            for (f in data) {
                f.pow(2f)
            }
        }
    }

    // NOTE: Using accumulator and println() to avoid compiler optimizations
    @Test
    fun pow2WithAcc() {
        benchmarkRule.measureRepeated {
            var result = 0f
            for (f in data) {
                result += f.pow(2f)
            }
            println(result)
        }
    }

    // NOTE: Using new API to accomplish the same
    // Observe: This gives similar results as above
    @OptIn(ExperimentalBlackHoleApi::class)
    @Test
    fun pow2WithBlackHole() {
        benchmarkRule.measureRepeated {
            for (f in data) {
                BlackHole.consume(f.pow(2f))
            }
        }
    }

    // NOTE: Try to optimize the code. Using "f * f" instead of pow()
    // Observe: "f * f" is faster.
    // Question: Why is "f * f" faster?
    @OptIn(ExperimentalBlackHoleApi::class)
    @Test
    fun squareWithBlackHole() {
        benchmarkRule.measureRepeated {
            for (f in data) {
                BlackHole.consume(f * f)
            }
        }
    }
}