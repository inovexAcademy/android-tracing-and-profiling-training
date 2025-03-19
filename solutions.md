# Solutions

This file contains tips and tricks and partial solutions for the exercises.


## Custom Trace Events

Example java code for a custom trace event:

    import androidx.compose.ui.util.trace
    [...]

    fun updateCounter() {
        trace("updateCounter") {
            counter += getAccFromNative()

            runOnUiThread {
                textViewCounter.text = "$counter"
            }
        }
    }

Example C/C++ code for a custom trace event:

    JNIEXPORT jint JNICALL
    Java_com_inovex_training_performance_CustomTraceEventsActivity_getAccFromNative(UNUSED JNIEnv *env,
                                                                                    UNUSED jobject thiz) {
        ATrace_beginSection("getAccFromNative");

        jint value = 42 / 2;
        usleep(100);

        ATrace_endSection();

        return value;
    }

Question: What is the green/grey line a top of the timer thread? And why is it
green-gray-green?

Answer: It's the thread's proces state (sleeping, running, runable). The gray
gap exists because the code uses `usleep(100)`. While waiting 100ms the thread
is put into the sleep state.


### CPU Profiling

Question: Why do you see the "wired" pattern in the callstack of `fibStd()`.

Answer: The function `fibStd()` is _double_ recursive. That's why it has this
"wired" pattern.


## Java Memory Profiling

Question: How to avoid expensive allocations for objects that often used?

Answer: Use an object pool.

Bonus question: What are the different types of heaps?

Answer: See the documentation at

    https://developer.android.com/studio/profile/capture-heap-dump


### Native Memory Profiling

No solution here.


## Micro Benchmark

Result of comparing LinkedHashMap and HashMap in `fibCaching()`

    HashMap
    7,164,410   ns       77900 allocs    Trace    FibBenchmark.fibCaching
    7,098,802   ns       77900 allocs    Trace    FibBenchmark.fibCaching

    LinkedHashMap
    8,896,345   ns       77900 allocs    Trace    FibBenchmark.fibCaching
    8,854,206   ns       77900 allocs    Trace    FibBenchmark.fibCaching

So there is a difference for the `fib(42)`.

Bonus question: Is this difference dependend on the parameter `n`?

Results with Long in `fibFast()`

    81,118   ns           0 allocs    Trace    FibBenchmark.fibFast
    81,111   ns           0 allocs    Trace    FibBenchmark.fibFast

Using Int instead of Long in `fibFast()`

    fun fibFast(n: Int): Int {
        var a = 0
        var b = 1
        for (i in 0..<n) {
            val aTmp = b
            b += a
            a = aTmp
        }
        return a
    }

    // results
    81,115   ns           0 allocs    Trace    FibBenchmark.fibFast
    81,107   ns           0 allocs    Trace    FibBenchmark.fibFast

No performance gain. Mostly because the architecture is 64-bit.

Trying a while instead of a for-loop

    fun fibFast( n: Long): Long {
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

    // results
    81,111   ns           0 allocs    Trace    FibBenchmark.fibFast
    81,119   ns           0 allocs    Trace    FibBenchmark.fibFast

Also no performance gain!

Comparing the DEX code shows that there are difference in the instructions and the length of the
code. There is one additional instruction.



## Macro Benchmark

Question: "How much does fibStd() add to the start up latency"

Example code

        val fib = trace("fibStd") {
            fibStd(23 + 10)
        }

Answer: No, much. On my device it's only adding 83ms to the 1.5 seconds startup
time.


## JNI Performance

A example tracing implementation for the different `add*` native functions is

    import androidx.compose.ui.util.trace
    [...]

    fun updateCounter() {
        var value = 0

        trace("add") {
            value += add(21, 21)
        }
        trace("addFastNative") {
            value += addFastNative(21, 21)
        }
        trace("addCriticalNative") {
            value += addCriticalNative(21, 21)
        }

        counter += value
        runOnUiThread {
            textViewCounter.text = "$counter"
        }
    }

Select "Capture System Activities/System Trace" and capture for some seconds.

Now there are multiple ways forward. First you can one single trace event in
the UI. This should already show that e.g. `@CriticalNative` is the fastest.

Second: With clicks trough the right hops, you can also see a summary with
count, average, max, min and std dev for all events of the same trace event.

The results should be something like

* `add` average: 53 us
* `addFastNative` average: 21 us
* `addCriticalNative` average: 15 us

So `@CriticalNative` is really the fastest.
