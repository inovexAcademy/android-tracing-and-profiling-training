# Android Tracing and Profiling Training

Example and training application for the Tracing and Profiling Training for
Android from inovex.


## Running on virtual devices

If you use a virtual device/emulator you must surpress some safe guards in the
Android Benchmark library. See file `app/build.gradle.kts`. Otherwise the
benchmark tests will fail.
