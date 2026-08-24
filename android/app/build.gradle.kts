plugins {
    id("com.android.application")
}

android {
    namespace = "com.pubg.pubgforwatch"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pubg.pubgforwatch"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "0.0.1Beta"

        ndk {
            abiFilters += listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
        }
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packaging {
        jniLibs {
            useLegacyPackaging = false
        }
    }
}

afterEvaluate {
    applicationVariants.all {
        outputs.all {
            if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
                outputFileName = "PUBG-for-Watch-${versionName}.apk"
            }
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}
