// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH

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

#define UNUSED __attribute__((unused))

#define TAG "native"
#define ALOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
#define ALOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, TAG, __VA_ARGS__)
#define ALOGW(...) __android_log_print(ANDROID_LOG_WARN, TAG, __VA_ARGS__)
#define ALOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

JNIEXPORT jint JNICALL
Java_com_inovex_training_performance_MainActivity_getSomeNumberFromNative(UNUSED JNIEnv *env,
                                                                          UNUSED jobject thiz) {
    ALOGV("here in function %s", __func__);

    return 42;
}

JNIEXPORT jint JNICALL
Java_com_inovex_training_performance_MainActivity_getAccFromNative(UNUSED JNIEnv *env,
                                                                   UNUSED jobject thiz) {
    ATrace_beginSection("native-getIntFromNative");

    jint value = 1;

    ATrace_endSection();

    return value;
}
