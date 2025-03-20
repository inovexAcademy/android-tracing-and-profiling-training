// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License: MIT

package com.inovex.training.performance

import org.junit.Test
import java.math.BigInteger
import kotlin.time.measureTime

class TailRecursionTest {

    private fun calculateFactorialRecursive(n: BigInteger): BigInteger {
        return if (n == BigInteger.ONE) {
            n
        } else {
            n.multiply(calculateFactorialRecursive(n.minus(BigInteger.ONE)))
        }
    }

    private tailrec fun calculateFactorialTailRec(
        n: BigInteger,
        acc: BigInteger = BigInteger.ONE
    ): BigInteger {
        return if (n == BigInteger.ONE) {
            acc
        } else {
            calculateFactorialTailRec(n.minus(BigInteger.ONE), n.multiply(acc))
        }
    }

    @Test
    fun testFactorial() {
        val duration = measureTime {
            calculateFactorialRecursive(BigInteger("5000"))
            // calculateFactorialRecursive(BigInteger("10000"))
        }
        println("factorial duration: $duration")
    }

    @Test
    fun testFactorialTailRec() {
        val duration = measureTime {
            calculateFactorialTailRec(BigInteger("5000"))
            // calculateFactorialTailRec(BigInteger("10000"))
        }
        println("factorial duration: $duration")
    }

    @Test
    fun testFactorialLoop() {
        val duration = measureTime {
            calculateFactorial(5000)
            // calculateFactorialTailRec(BigInteger("10000"))
        }
        println("factorial duration: $duration")
    }
}