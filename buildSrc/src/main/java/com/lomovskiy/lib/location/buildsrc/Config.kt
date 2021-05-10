package com.lomovskiy.lib.location.buildsrc

object Config {

    object Publish {
        const val groupId = "com.lomovskiy.lib"
        const val artifactId = "location"
    }

    object GradlePlugins {

        const val android = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinStd}"

    }

    object Versions {

        const val code = 1
        const val name = "1.0"

        object Android {

            const val target = 30
            const val compile = 30
            const val min = 16

        }

        const val androidGradlePlugin = "4.2.0"
        const val buildTools = "30.0.3"

        const val kotlinStd = "1.5.0"
        const val kotlinCoroutines = "1.5.0-RC"
        const val ui = "1.0.12"
        const val androidXCore = "1.6.0-alpha03"

    }

    object Deps {

        const val kotlinStd = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinStd}"
        const val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
        const val ui = "com.github.lomovskiy:android-lib-ui:${Versions.ui}"
        const val androidXCore = "androidx.core:core-ktx:${Versions.androidXCore}"

    }

}
