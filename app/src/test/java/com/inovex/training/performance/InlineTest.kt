// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License: MIT

package com.inovex.training.performance

import org.junit.Test
import kotlin.time.measureTime

class InlineTest {

    private inline fun repeatInline(n: Int, block: (Int) -> Unit) {
        for (index in 0 until n) {
            block(index)
        }
    }

    private fun repeatNonInline(n: Int, block: (Int) -> Unit) {
        for (index in 0 until n) {
            block(index)
        }
    }

    @Test
    fun inlinePerformance() {
        val duration = measureTime {
            var x = 0L
            repeatInline(1_000_000_000) {
                x += it
            }
        }
        println("inline duration: $duration")
    }

    @Test
    fun nonInlinePerformance() {
        val duration = measureTime {
            var x = 0L
            repeatNonInline(1_000_000_000) {
                x += it
            }
        }
        println("non inline duration: $duration")
    }
}