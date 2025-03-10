// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License: MIT

package com.inovex.training.performance

// The standard fibonacci implementation
fun fibStd(n: Long): Long {
    assert(n >= 0L)

    if (n == 0L) return 0
    if (n == 1L) return 1
    return fibStd(n - 1L) + fibStd(n - 2L)
}

// A faster fibonacci implementation
fun fibFast(n: Long): Long {
    var a = 0L
    var b = 1L
    for (i in 0L..<n) {
        val aTmp = b
        b += a
        a = aTmp
    }
    return a
}