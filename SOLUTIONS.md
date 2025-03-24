# Solutions

This file contains tips and tricks and partial solutions for the exercises.


## (1) Custom Trace Events

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


## (2) CPU Profiling

Question: Why do you see the "wired" pattern in the callstack of `fibStd()`.

Answer: The function `fibStd()` is _double_ recursive. That's why it has this
"wired" pattern.


## (3) Java Memory Profiling

Question: How to avoid expensive allocations for objects that often used?

Answer: Use an object pool.

Bonus question: What are the different types of heaps?

Answer: See the documentation at

    https://developer.android.com/studio/profile/capture-heap-dump


## (4) Native Memory Profiling

No solution here.


## (5) Micro Benchmark - First Part

No solution here.


## (6) Micro Benchmark - Second Part

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


## (7) Macro Benchmark

Question: "How much does fibStd() add to the start up latency"

Example code

        val fib = trace("fibStd") {
            fibStd(23 + 10)
        }

Answer: No, much. On my device it's only adding 83ms to the 1.5 seconds startup
time.


## (8) JNI performance

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


## (9) Compose performance

No solution yet here!


## (10) Kotlin performance

No solution here.


## (11) Coroutine scheduling

Question: On which threads are these executed?

Answer:
* updateCounterBackground() is running on the timer thread
* heavyFunction() is running on the main/UI thread

And the heavyFunction()s are run sequentially on the main thread. There is no
parallelism.

Question: When does this happen?

After the heavyFunction()'s are finished.

After switching to the `IO` Dispatcher, the heavyFunction() ran in parallel.

Final code with custom trace tags

    import androidx.compose.ui.util.trace
    [...]
    private fun heavyFunction(): Int {
        trace("heavyFunction") {
            val startTimeInMs = System.currentTimeMillis()
            var acc = 0
            // Compute for 20 milliseconds
            while (System.currentTimeMillis() < startTimeInMs + 20) {
                acc += 1
            }
            return acc
        }
    }

    private fun startHeavyTask(): Deferred<Int> {
        return scope.async(Dispatchers.IO) { heavyFunction() }
    }

    fun updateCounterBackground() {
        trace("updateCounterBackground") {
            val job1 = startHeavyTask()
            val job2 = startHeavyTask()
            val job3 = startHeavyTask()

            scope.launch(Dispatchers.Main) {
                counter += job1.await() + job2.await() + job3.await()
                trace("set counter") {
                    textViewCounter.text = "$counter"
                }
            }
        }
    }


## (12) Coroutine start latency

Final code

    import android.os.Trace
    [...]
    private var cookie = 0
    [...]
    private fun heavyFunction(cookie: Int): Int {
        Trace.endAsyncSection("startHeavyTask", cookie)
	[...]
    }

    private fun startHeavyTask(): Deferred<Int> {
        val cookieUsed = cookie
        cookie += 1
        Trace.beginAsyncSection("startHeavyTask", cookieUsed)
        return scope.async {
            heavyFunction(cookieUsed)
        }
    }

For my trace the average duration was 1.14 ms for 75 occurrences on a Pixel2
phone.

Question: Is this fast or slow?

A 60hz display can draw a new frame every 16ms. So 1.14 ms is already quite
long.

Question: How does this change when the `Main` and not the `IO` dispatcher is used?

It increases dramatically since the coroutines are executed sequentially on the
main thread. e.g. I measure 19.9ms on average.
