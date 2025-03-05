plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.inovex.training.performance"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.inovex.training.performance"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        // Already switched to the AndroidBenchmarkRunner runner for performance testing
        //testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "androidx.benchmark.junit4.AndroidBenchmarkRunner"

        // If you use a virtual device instead of a physical device, you must surpress some
        // errors from the Benchmark tooling
        // See
        //  * https://stackoverflow.com/questions/74280538/testinstrumentationrunnerarguments-is-deprecated-and-replaced-by-property
        //  * https://stackoverflow.com/questions/61593995/not-using-isolationactivity-via-androidbenchmarkrunner
        //testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] = "EMULATOR"

        vectorDrawables {
            useSupportLibrary = true
        }

        ndk {
            // Just build for the most important archs
            abiFilters += listOf( "x86_64", "arm64-v8a")
        }
    }

    buildTypes {
        debug {
            isDebuggable = false
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.benchmark.junit4)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
