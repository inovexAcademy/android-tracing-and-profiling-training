// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License-Identifier: MIT

#include <android/log.h>
#include <android/trace.h>
#include <jni.h>

// TODO(slengfeld): refactor duplicate definitions
#define UNUSED __attribute__((unused))

#define TAG "java_leak_activity"

// TODO(slengfeld): refactor duplicate definitions
#define ALOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
#define ALOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, TAG, __VA_ARGS__)
#define ALOGW(...) __android_log_print(ANDROID_LOG_WARN, TAG, __VA_ARGS__)
#define ALOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

JNIEXPORT void JNICALL
Java_com_inovex_training_performance_JavaLeakActivity_leakReference(JNIEnv *env, UNUSED jobject thiz,
        jobject o) {
    (*env)->NewGlobalRef(env, o);
}