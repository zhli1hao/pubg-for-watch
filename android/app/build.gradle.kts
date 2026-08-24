plugins { id("com.android.application") }
android {
    namespace = "com.pubg.pubgforwatch"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.pubg.pubgforwatch"
        minSdk = 24; targetSdk = 34
        versionCode = 1; versionName = "0.0.1-Beta"
        ndk { abiFilters += listOf("armeabi-v7a","arm64-v8a","x86","x86_64") }
        resourceConfigs += listOf("zh","en")
    }
    buildTypes { getByName("debug") { isDebuggable = true } }
    packaging { jniLibs { useLegacyPackaging = false } }
}
