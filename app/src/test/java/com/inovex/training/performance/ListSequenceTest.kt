// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License: MIT

package com.inovex.training.performance

import org.junit.Test
import kotlin.time.measureTimedValue

class ListSequenceTest {

    @Test
    fun listPerformance() {
        val (result, duration) = measureTimedValue {
            (5_000 downTo 0).toList().map {
                calculateFactorial(it)
            }.take(5)
        }
        println(result)
        println("list duration: $duration")
    }

    @Test
    fun sequencePerformance() {
        val (result, duration) = measureTimedValue {
            (5_000 downTo 0).toList().asSequence().map {
                calculateFactorial(it)
            }.take(5).toList()
        }
        println(result)
        println("sequence duration: $duration")
    }
}