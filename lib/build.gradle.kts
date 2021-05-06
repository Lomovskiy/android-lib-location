import com.lomovskiy.lib.location.buildsrc.Config

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("maven-publish")
}

android {

    defaultConfig {
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

}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs)
}

artifacts {
    archives(sourcesJar)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = com.lomovskiy.lib.location.buildsrc.Config.Publish.groupId
                artifactId = com.lomovskiy.lib.location.buildsrc.Config.Publish.artifactId
                version = com.lomovskiy.lib.location.buildsrc.Config.Versions.name
            }
        }
    }
}
