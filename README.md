# Android Tracing and Profiling Training

Example and training application for the Tracing and Profiling Training for
Android from inovex.

If you are interested in this training course, please send an email to
[academy@inovex.de](mailto:academy@inovex.de).


# Running on virtual devices

If you use a virtual device/emulator you must suppress some safe guards in the
Android Benchmark library. See files

    app/MicroBenchmark/build.gradle.kts
    app/MacroBenchmark/build.gradle.kts

Otherwise the benchmark tests will fail.


# General notes for Performance Optimizations

Performance optimization is like scientific research:

1) Formulate hypotheses
2) Design and run performance test
3) Analyze test results
4) Improve code
6) Repeat

General notes:

* Avoid premature optimization. Simple code is also a value!
* Know the [90/10 rule][wiki]: 90% of time is spent in 10% of the code
* Start optimizing the biggest issues first. Gives the most return
* In the long run: Avoid performance regression trough performance testing in
  CI

Some quotes:

> premature optimization is the root of all evil (or at least most of it) in
> programming.

by [Donald Knuth][knuth]


> The first 50% of the performance improvements are low hanging fruits and
> easy. The last 50% are hard and need architectural changes.

by random developer on the internet


# Perfetto Introduction

See documentation

* https://developer.android.com/topic/performance/tracing
* https://developer.android.com/topic/performance/tracing/navigate-report

And see page 29 to 34 on slides:

   https://www.inovex.de/wp-content/uploads/OSS-Talk-Advanced-System-Profiling-Tracing-and-Trace-Analysis-with-Perfetto.pdf


# Exercises

## (1) Custom Trace Events

See documentation

* https://developer.android.com/topic/performance/tracing/custom-events
* https://developer.android.com/topic/performance/tracing/custom-events-native
* https://developer.android.com/reference/android/os/Trace

See file

    app/src/main/java/com/inovex/training/performance/CustomTraceEventsActivity.kt
    app/src/main/cpp/custom_trace_events_activity.c

Exercise: Add custom trace event in Java function `updateCounter()` and in
native function.  Use `trace()` in Kotlin and
`ATrace_beginSection()/ATrace_endSection()` in C/C++.

Start application as "profileable" and use "Capture System Activities / System
Trace" and capture for some seconds.

Find you custom defined trace events in the trace view.

Tip: search on the thread `Timer-0`

Question: What is the green/grey line a top of the timer thread? And why is it
green-grey-green?


## (2) CPU Profiling

See documentation

* https://developer.android.com/studio/profile/sample-callstack

See file

    app/src/main/java/com/inovex/training/performance/CPULeakActivity.kt
    app/fiblibrary/src/main/java/com/inovex/fib/fib.kt

Use "Find CPU Hotspots / Callstack Sample". Start the application as
"profileable".

Look at trace view. See that `fibStd()` dominates the call stack/flame graph.

Question: Why do you see the "wired" pattern in the callstack of `fibStd()`.

Use "Find CPU Hotspots / Java/Kotlin Method Recording".

Change implementation to `fibFast()` and look at CPU hotspots/function call
stacks again. Do you find the `fibFast()`  call site?

Bonus: Increase the argument to `fibFast()` to see it in the call stack again.


## (3) Java Memory Profiling

See documentation

* https://developer.android.com/studio/profile/capture-heap-dump

See file

    app/src/main/java/com/inovex/training/performance/JavaLeakActivity.kt

Start application and use "Analyze Memory / Heap Dump". This makes a oneshot
heap dump. Note: Application cannot be "profileable". It must be "debuggable".

See that there are a lot of Integer objects. You can even see the value of
every Integer object.

Also see the `int[]` array objects.

Now use "Track Memory Consumption / Java/Kotlin Allocations" to get a life
recording memory allocations.

Let the application run. See the general memory consumption grow until the GC
kicks in. Notice the trash icon in the graph!

Notice that

* multiple different types of allocations are tracked: java, native, graphics,
  stack, code ...
* you see even more information for an object than above. E.g. the call stack
  (class name, function and line) where the object was created. And the
  allocation and deallocation time.
* you can select a timeframe to see only the allocations and deallocation in
  that period.
* on the tab "Visualization" you can see a flame graph according to the call
  stack.

Question: How to avoid expensive allocations for objects that often used?

Bonus question: What are the different types of heaps?


## (4) Native Memory Profiling

See documentation

* https://developer.android.com/studio/profile/record-native-allocations

See file

    app/src/main/java/com/inovex/training/performance/NativeLeakActivity.kt

Use "Track Memory Consumption / Native Allocations". Start the application as
"profileable".

See the "Visualization" tab. `calloc` should dominate.

In tab "Table" switch to "Arrange by callstack". Find `calloc()`.


## (5) Micro Benchmark - First Part

See documentation

* https://developer.android.com/topic/performance/benchmarking/microbenchmark-overview

Example based on and further reading

* https://www.romainguy.dev/posts/2024/you-are-going-to-need-it/
* https://www.romainguy.dev/posts/

See file

    app/MicroBenchmark/src/androidTest/java/com/inovex/microbenchmark/MathBenchmark.kt

Execute all benchmarks. You see an output like

    For more information, see https://issuetracker.google.com/issues/316174880
          406,056   ns           0 allocs    Trace    MathBenchmark.pow2WithBlackHole
    Pixel 2 - 11 Tests 1/4 completed. (0 skipped) (0 failed)
          405,201   ns           2 allocs    Trace    MathBenchmark.pow2WithAcc
    Pixel 2 - 11 Tests 2/4 completed. (0 skipped) (0 failed)
           27,856   ns           0 allocs    Trace    MathBenchmark.pow2ButInvalidBenchmark
    Pixel 2 - 11 Tests 3/4 completed. (0 skipped) (0 failed)
           56,815   ns           0 allocs    Trace    MathBenchmark.squareWithBlackHole
    Finished 4 tests on Pixel 2 - 11

Go through the functions in the file, read the comments and look at the sample
results.


## (6) Micro Benchmark - Second Part

See file

    app/MicroBenchmark/src/androidTest/java/com/inovex/microbenchmark/FibBenchmark.kt

Run all three benchmarks:

See the timing information and the `allocs`. Do `fibStd` and `fibFast` no
memory allocations?

Bonus question: So they cannot ran out of memory?


Two Tasks:

1) Compare `LinkedHashMap` against `HashMap` in `fibCaching()`
2) Compare `Long` vs `Int` and `for` vs `while`.

Next exercise: In the file

    app/fiblibrary/src/main/java/com/inovex/fib/fib.kt

press Ctrl-Shift-A and perform the action `Show Kotlin ByteCode`

Next Bonus Exercise: Come up with a additional idea/code change to speed up
`fibFast()`


## (7) Macro Benchmark

Examples based on (and upstream resources):

* https://developer.android.com/topic/performance/benchmarking/macrobenchmark-overview
* https://developer.android.com/codelabs/android-macrobenchmark-inspect#0

Exercise: Measure activity start time and trace the issue

See files

    app/MacroBenchmark/src/main/java/com/inovex/macrobenchmark/MacroBenchmarkActivityBenchmark.kt
    app/src/main/java/com/inovex/training/performance/MacroBenchmarkActivity.kt

Launch MarcoBenchmark for the first time. You should see an output like

    MacroBenchmarkActivityBenchmark_startup
    timeToInitialDisplayMs   min 1,537.8,   median 1,569.2,   max 1,600.5
    Traces: Iteration 0 1

Is the testing stable? Min and max not too far apart?

Now you can click on `0`, `1` or the ms numbers to debug the issue in the trace
view. What takes the longest? Is it the `onCreate()` method?

Add a trace event around the `fibStd()` call in the `onCreate()` function.
How much does it contribute to the start up latency?

Change the argument to `fibStd()` to make the startup longer and shorter and
check the trace again.


## (8) JNI performance

This exercises is about JNI performance. But it's also a generic example on how
to use tracing for profiling performance. E.g. for small experiments you don't
need MacroBenchmark or MicroBenchmark.

Android Documentation about JNI Performance:

* https://developer.android.com/training/articles/perf-jni
* https://developer.android.com/training/articles/perf-jni#fast_critical_native

Exercise: Compare the different JNI calling convention *no annotation*,
`@FastNative` and `@CriticalNative` for the calling overhead. Is
`@FastNative`faster and is `@CriticalNative` the fastest?

Start in file

    app/src/main/java/com/inovex/training/performance/JNIPerformanceActivity.kt

and add trace events.

First compare the timining of a single call for every function.

Second compare the avearge time of multiple calls.


## (9) Compose performance

See file

   app/src/main/java/com/inovex/training/performance/compose/ComposeActivity.kt

and additional instructions by the trainer.


## (10) Kotlin performance

See files in the directory

    app/src/test/java/com/inovex/training/performance/

and the additional instructions be the trainer.


## (11) Coroutine scheduling

See file

    app/src/main/java/com/inovex/training/performance/CoroutineTraceActivity.kt

Add a custom traces in function

* updateCounterBackground()
* heavyFunction()

Question: On which threads are these executed?

Add custom trace to see when

    textViewCounter.text = "$counter"

is executed.

Question: When does this happen?

Now switch the dispatcher for the heavy task to `IO`. Code is

    private fun startHeavyTask(): Deferred<Int> {
        return scope.async(Dispatchers.IO) { heavyFunction() }
    }

On which threads is the heavy task executed now?


## (12) Coroutine start latency

The goal in this example is to measure the startup latency of kotlin coroutines
with the AsyncSection API of the Android trace API.

* https://developer.android.com/ndk/reference/group/tracing
* https://developer.android.com/reference/android/os/Trace

See file

    app/src/main/java/com/inovex/training/performance/CoroutineTraceActivity.kt

Add a `beginAsyncSection()` in function `startHeavyTask()`. The `cookie` can be
an integer that is incremented on every call.

Add a `endAsyncSection()` in function `heavyFunction()`.

And trace the result with perfetto. You should see an extra track/lane for your
custom AsyncSection name.

Trace for some seconds and then look at the average value.

Question: Is this fast or slow?

Question: How does this change when the `Main` and not the `IO` dispatcher is used?


## (13) Frame Jank

See file

    app/src/main/java/com/inovex/training/performance/FrameJankActivity.kt

Note: This example uses the
[Android Choreographer](https://developer.android.com/reference/android/view/Choreographer)
to sync with the VSYNCS (vertical syncs) of the system instead of a custom
timer that executes at not aligned intervals.

Look at the source code. Every 10 frames a very long string is inject into the
UI to provoke janky frame.

Exercise: Start the app and trace with perfetto for 1-2 seconds. Enable

* "Scheduling Details"
* All GPU data sources
* "Atrace userspace annotations". Don't filter, select all. And
  enable "Record events from all Android apps and services"

Try to see the following (may depend on our device and emulator vs physical):

* See the irregular pattern in the lanes/tracks of the application.
  There is a "pause" every 10 frames.
* See the custom `doFrame` and `veryLongString` trace tags
* Observe that the "pause" is after the `veryLongString` trace tag ;-)

Question: What causes the long rendering time?

Now look the track/lane "GPU completion". NOTE: There are two similar named
tracks/lanes.  One is for the thread and the other for the "GPU completion"
counter/datasource.

Question: Does the GPU spend more or less time for the janky frame?

Now observe the draw chain that happen each after another on different threads

* See the `Choreographer#doFrame` on the main thread
* right afterwards see the `DrawFrame` on the render thread
* right afterwards see the `waiting for GPU completion` on the GPU thread
  and see the `GPU completion` lane/track

In the track of the RenderThread click on the `binder transaction` trace. See

* the destination pid and other information about the binder call in the bottom
  half of the window.
* See the "flow" (=arrow) to another track in another process

Question: Who is this other process and what it's purpose?

Now look at the lane `VSNYC-app`. Pin lane and move the window to the tracing
app again. The "pin" you can see on the left side of the track/lane
description.

Question: How does the `VSNYC-app` lane correlate to the main thread?

Bonus exercise: Try out the perfetto [Frame
timeline](https://perfetto.dev/docs/data-sources/frametimeline) feature if you
have a Android 12+ device:

See the two new tracks/lanes `Expected Timeline` and `Actual Timeline`. And see
the different colors. The janky frame should be red. The other frames green.


[wiki]: https://en.wikipedia.org/wiki/Program_optimization#Bottlenecks
[knuth]: https://en.wikiquote.org/wiki/Donald_Knuth
