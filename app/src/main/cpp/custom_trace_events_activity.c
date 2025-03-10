// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License-Identifier: MIT

#include <unistd.h>

#include <android/log.h>
#include <android/trace.h>
#include <jni.h>

// TODO(slengfeld): refactor duplicate definitions
#define UNUSED __attribute__((unused))

// TODO(slengfeld): refactor duplicate definitions
#define TAG "custom_trace_events_activity"
#define ALOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
#define ALOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, TAG, __VA_ARGS__)
#define ALOGW(...) __android_log_print(ANDROID_LOG_WARN, TAG, __VA_ARGS__)
#define ALOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

JNIEXPORT jint JNICALL
Java_com_inovex_training_performance_CustomTraceEventsActivity_getAccFromNative(UNUSED JNIEnv *env,
                                                                                UNUSED jobject thiz) {
    jint value = 42 / 2;
    usleep(100);

    return value;
}
