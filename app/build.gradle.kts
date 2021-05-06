import com.lomovskiy.lib.location.buildsrc.Config

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {

    defaultConfig {
        applicationId("com.lomovskiy.lib.location.sample")
        minSdkVersion(com.lomovskiy.lib.location.buildsrc.Config.Versions.Android.min)
        targetSdkVersion(com.lomovskiy.lib.location.buildsrc.Config.Versions.Android.target)
        compileSdkVersion(com.lomovskiy.lib.location.buildsrc.Config.Versions.Android.compile)
        buildToolsVersion(com.lomovskiy.lib.location.buildsrc.Config.Versions.buildTools)
        versionCode(com.lomovskiy.lib.location.buildsrc.Config.Versions.code)
        versionName(com.lomovskiy.lib.location.buildsrc.Config.Versions.name)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(com.lomovskiy.lib.location.buildsrc.Config.Deps.kotlinStd)
    implementation(com.lomovskiy.lib.location.buildsrc.Config.Deps.ui)
}