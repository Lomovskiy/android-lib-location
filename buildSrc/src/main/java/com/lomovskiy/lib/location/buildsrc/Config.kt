package com.lomovskiy.lib.location.buildsrc

object Config {

    object Publish {
        const val groupId = "com.lomovskiy.lib"
        const val artifactId = "location"
    }

    object GradlePlugins {

        const val android = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

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

        const val kotlin = "1.4.30"

        const val ui = "1.0.6"

    }

    object Modules {

        const val app = ":app"
        const val lib = ":lib"

    }

    object Deps {

        const val kotlinStd = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        const val ui = "com.github.lomovskiy:android-lib-ui:${Versions.ui}"

    }

}
