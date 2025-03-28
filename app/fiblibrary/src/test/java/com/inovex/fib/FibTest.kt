package com.inovex.fib

import org.junit.Test

import org.junit.Assert.*

class FibTest {
    @Test
    fun testFib() {
        assertEquals(0, fibStd(0))
        assertEquals(1, fibStd(1))
        assertEquals(1, fibStd(2))
        assertEquals(2, fibStd(3))
        assertEquals(3, fibStd(4))
        assertEquals(5, fibStd(5))
        assertEquals(8, fibStd(6))
    }

    @Test
    fun testFibCaching() {
        assertEquals(0, fibCaching(0))
        assertEquals(1, fibCaching(1))
        assertEquals(1, fibCaching(2))
        assertEquals(2, fibCaching(3))
        assertEquals(3, fibCaching(4))
        assertEquals(5, fibCaching(5))
        assertEquals(8, fibCaching(6))
    }

    @Test
    fun testFibFast() {
        assertEquals(0, fibFast(0))
        assertEquals(1, fibFast(1))
        assertEquals(1, fibFast(2))
        assertEquals(2, fibFast(3))
        assertEquals(3, fibFast(4))
        assertEquals(5, fibFast(5))
        assertEquals(8, fibFast(6))
    }
}