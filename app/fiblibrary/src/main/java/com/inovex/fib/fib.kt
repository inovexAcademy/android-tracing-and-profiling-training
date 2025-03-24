// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License: MIT

package com.inovex.fib

import java.util.HashMap

// The standard fibonacci implementation
fun fibStd(n: Long): Long {
    assert(n >= 0L)

    if (n == 0L) return 0
    if (n == 1L) return 1
    return fibStd(n - 1L) + fibStd(n - 2L)
}

// A faster fibonacci implementation
fun fibCaching(n: Long): Long {
    val cache = HashMap<Long, Long>()

    fun fibInternal(n : Long): Long {
        if (n <= 1)  return n

        if (cache.containsKey(n)) {
            return cache[n]!!
        }

        val result = fibInternal(n - 1) + fibInternal(n - 2)
        cache[n] = result
        return result
    }

    return fibInternal(n)
}

// A even faster fibonacci implementation
fun fibFast( n: Long): Long {
    // Idee: implement a cache for the fast 100 numbres
    var a = 0L
    var b = 1L
    var n2 = n
    while (n2 > 0) {
        val aTmp = b
        b += a
        a = aTmp
        n2 -= 1
    }
    return a
}
