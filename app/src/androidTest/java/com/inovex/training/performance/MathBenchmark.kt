// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH

package com.inovex.training.performance

import androidx.benchmark.BlackHole
import androidx.benchmark.ExperimentalBlackHoleApi
import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.pow

// This method is based on the upstream example
// https://www.romainguy.dev/posts/2024/you-are-going-to-need-it/
@RunWith(AndroidJUnit4::class)
class MathBenchmark {
    @get:Rule
    val benchmarkRule = BenchmarkRule()

    private val data = FloatArray(8_192) {
        it.toFloat() / 3f
    }

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

    @Test
    fun squareWithAcc() {
        benchmarkRule.measureRepeated {
            var result = 0f
            for (f in data) {
                result += f * f
            }
            println(result)
        }
    }

    @OptIn(ExperimentalBlackHoleApi::class)
    @Test
    fun pow2WithBlackHole() {
        benchmarkRule.measureRepeated {
            for (f in data) {
                BlackHole.consume(f.pow(2f))
            }
        }
    }

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