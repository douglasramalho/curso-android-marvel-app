package com.example.buildsrc

object Dependencies {

    object Classpaths {
        const val gradle = "com.android.tools.build:gradle:${Version.Classpaths.gradle}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.Classpaths.kotlin}"
        const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Version.Classpaths.hilt}"
        const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Version.Classpaths.safeArgs}"

        val list = listOf(
            gradle,
            kotlin,
            hilt,
            safeArgs
        )
    }

    object BoM {
        const val okHttp = "com.squareup.okhttp3:okhttp-bom:${Version.BoM.okHttp}"
    }

    object Libs {
        const val core = "androidx.core:core-ktx:${Version.Libs.core}"
        const val appCompat = "androidx.appcompat:appcompat:${Version.Libs.appCompat}"
        const val constraint = "androidx.constraintlayout:constraintlayout:${Version.Libs.constraint}"
        const val material = "com.google.android.material:material:${Version.Libs.material}"
        const val navFragment = "androidx.navigation:navigation-fragment-ktx:${Version.Libs.navigation}"
        const val navUi = "androidx.navigation:navigation-ui-ktx:${Version.Libs.navigation}"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.Libs.lifecycle}"
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Version.Libs.lifecycle}"
        const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.Libs.lifecycle}"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.Libs.coroutines}"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.Libs.coroutines}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${Version.Libs.retrofit}"
        const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Version.Libs.retrofit}"
        const val okHttp = "com.squareup.okhttp3:okhttp"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor"
        const val hilt = "com.google.dagger:hilt-android:${Version.Libs.hilt}"
        const val room = "androidx.room:room-ktx:${Version.Libs.room}"
        const val roomRuntime = "androidx.room:room-runtime:${Version.Libs.room}"
        const val paging = "androidx.paging:paging-runtime-ktx:${Version.Libs.paging}"
        const val glide = "com.github.bumptech.glide:glide:${Version.Libs.glide}"
        const val shimmer = "com.facebook.shimmer:shimmer:${Version.Libs.shimmer}"
        const val gson = "com.google.code.gson:gson:${Version.Libs.gson}"
        const val dataStore = "androidx.datastore:datastore-preferences:${Version.Libs.dataStore}"
    }

    object Kapt {
        const val hilt = "com.google.dagger:hilt-android-compiler:${Version.Libs.hilt}"
        const val room = "androidx.room:room-compiler:${Version.Libs.room}"
        const val glide = "com.github.bumptech.glide:compiler:${Version.Libs.glide}"
    }

    object TestLibs {
        const val junit = "junit:junit:${Version.TestLibs.junit}"
        const val core = "androidx.arch.core:core-testing:${Version.TestLibs.core}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.TestLibs.coroutines}"
        const val mockito = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Version.TestLibs.mockito}"
        const val room = "androidx.room:room-testing:${Version.TestLibs.room}"
    }

    object AndroidTestLibs {
        const val junit = "androidx.test.ext:junit:${Version.AndroidTestLibs.junit}"
        const val espresso = "androidx.test.espresso:espresso-core:${Version.AndroidTestLibs.espresso}"
    }

    object AppModule {
        val listLibs = listOf(
            Libs.core,
            Libs.appCompat,
            Libs.constraint,
            Libs.material,
            Libs.navFragment,
            Libs.navUi,
            Libs.viewModel,
            Libs.liveData,
            Libs.runtime,
            Libs.coroutinesCore,
            Libs.coroutinesAndroid,
            Libs.retrofit,
            Libs.retrofitGson,
            BoM.okHttp,
            Libs.okHttp,
            Libs.loggingInterceptor,
            Libs.hilt,
            Libs.room,
            Libs.roomRuntime,
            Libs.paging,
            Libs.glide,
            Libs.shimmer,
            Libs.gson,
            Libs.dataStore
        )

        val listKapt = listOf(
            Kapt.hilt,
            Kapt.room,
            Kapt.glide
        )

        val listTest = listOf(
            TestLibs.junit,
            TestLibs.core,
            TestLibs.coroutines,
            TestLibs.mockito,
            TestLibs.room
        )

        val listAndroidTest = listOf(
            AndroidTestLibs.junit,
            AndroidTestLibs.espresso
        )
    }
}