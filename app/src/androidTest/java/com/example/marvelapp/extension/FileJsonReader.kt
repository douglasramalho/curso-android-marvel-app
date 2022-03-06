package com.example.marvelapp.extension

import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltTestApplication
import java.io.IOException
import java.io.InputStreamReader

fun String.asJsonString(): String {
    try {
        val inputStream = (InstrumentationRegistry.getInstrumentation().targetContext
            .applicationContext as HiltTestApplication).assets.open(this)
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")
        reader.readLines().forEach {
            builder.append(it)
        }
        return builder.toString()
    } catch (e: IOException) {
        throw e
    }
}