package com.example.marvelapp

object Version {

    object Sdk {
        const val compileSdk = 31
        const val buildTools = "30.0.3"
        const val minSdk = 23
        const val targetSdk = 31
    }

    object App {
        const val versionCode = 1
        const val versionName = "1.0.0"
    }

    object Classpaths {
        const val gradle = "7.1.1"
        const val kotlin = "1.6.10"
        const val hilt = "2.41"
        const val safeArgs = "2.4.2"
    }

    object BoM {
        const val okHttp = "4.9.0"
    }

    object Libs {
        const val core = "1.7.0"
        const val appCompat = "1.4.1"
        const val constraint = "2.1.3"
        const val material = "1.6.0"
        const val navigation = "2.4.2"
        const val lifecycle = "2.4.1"
        const val coroutines = "1.5.1"
        const val retrofit = "2.9.0"
        const val hilt = "2.41"
        const val room = "2.4.2"
        const val paging = "3.1.1"
        const val glide = "4.12.0"
        const val shimmer = "0.5.0"
        const val gson = "2.8.9"
        const val dataStore = "1.0.0"
    }

    object TestLibs {
        const val junit = "4.13.2"
        const val core = "2.1.0"
        const val coroutines = "1.5.1"
        const val mockito = "2.2.0"
        const val room = "2.4.2"
    }

    object AndroidTestLibs {
        const val junit = "1.1.3"
        const val espresso = "3.4.0"
    }
}