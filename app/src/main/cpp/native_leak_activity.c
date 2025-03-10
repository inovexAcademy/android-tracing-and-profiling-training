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

#define TAG "native_leak_activity"

// TODO(slengfeld): refactor duplicate definitions
#define ALOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
#define ALOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, TAG, __VA_ARGS__)
#define ALOGW(...) __android_log_print(ANDROID_LOG_WARN, TAG, __VA_ARGS__)
#define ALOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

JNIEXPORT jint JNICALL
Java_com_inovex_training_performance_NativeLeakActivity_getSomeNumberFromNative(UNUSED JNIEnv *env,
                                                                                UNUSED jobject thiz,
                                                                                jint alloc_count) {
    //ALOGV("getSomeNumberFromNative() called");

    int *buffer = calloc(alloc_count, sizeof(int));

    for (int i = 0; i < alloc_count; i++)
        buffer[i] = i;

    int sum = 0;
    for (int i = 0; i < alloc_count; i++)
        sum += buffer[i];

    return sum;
}
