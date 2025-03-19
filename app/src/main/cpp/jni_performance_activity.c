// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License-Identifier: MIT

#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <errno.h>

#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>

#include <android/log.h>
#include <android/trace.h>
#include <jni.h>

// TODO(slengfeld): refactor duplicate definitions
#define UNUSED __attribute__((unused))

#define TAG "jni_performance_activity"

// TODO(slengfeld): refactor duplicate definitions
#define ALOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
#define ALOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, TAG, __VA_ARGS__)
#define ALOGW(...) __android_log_print(ANDROID_LOG_WARN, TAG, __VA_ARGS__)
#define ALOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

JNIEXPORT jint JNICALL
Java_com_inovex_training_performance_JNIPerformanceActivity_add(UNUSED JNIEnv *env,
                                                                UNUSED jobject thiz, jint a,
                                                                jint b) {
    return a + b;
}

JNIEXPORT jint JNICALL
Java_com_inovex_training_performance_JNIPerformanceActivity_addFastNative(UNUSED JNIEnv *env,
                                                                          UNUSED jobject thiz,
                                                                          jint a, jint b) {
    return a + b;
}

// NOTE: According to the documentation a '@CriticalNative' method does not has a JNIEnv and a
// jobject/jclass argument. But testing showed that there is an additional first argument present.
// Wired!
JNIEXPORT jint JNICALL
Java_com_inovex_training_performance_JNIPerformanceActivity_addCriticalNative(UNUSED JNIEnv *env,
                                                                              jint a, jint b) {
    // Testing function arguments using the following code
    ALOGV("addCriticalNative: a=%d b=%d", a, b);
    return a + b;
}
