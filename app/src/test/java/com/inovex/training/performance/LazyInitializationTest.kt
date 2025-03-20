// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License: MIT

package com.inovex.training.performance

import org.junit.Test
import kotlin.time.measureTime

class LazyInitializationTest {

    class A {
        val b = B()
        val c = C()
        val d = D()
    }
    
    class LazyA {
        val b by lazy { B() }
        val c by lazy { C() }
        val d by lazy { D() }
    }

    class B {
        init {
            calculateFactorial(100000)
        }
    }

    class C {
        init {
            calculateFactorial(50000)
        }
    }

    class D {
        init {
            calculateFactorial(20000)
        }
    }


    @Test
    fun eagerPerformance() {
        val duration = measureTime {
            A()
        }
        println("eager duration: $duration")
    }

    @Test
    fun lazyPerformance() {
        val duration = measureTime {
            LazyA()
        }
        println("lazy duration: $duration")
    }
}