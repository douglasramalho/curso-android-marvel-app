package com.github.coutinhonobre.core.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.math.BigInteger
import java.security.MessageDigest
import java.util.Calendar

class AuthorizationInterceptor(
    private val publicKey: String,
    private val privateKey: String,
    private val calendar: Calendar
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestUrl = request.url

        val timeInSeconds = (calendar.timeInMillis / 1000L).toString()
        val hash = "$timeInSeconds$privateKey$publicKey".md5()
        val newUrl = requestUrl.newBuilder()
            .addQueryParameter(QUERY_PARAMETER_TS, timeInSeconds)
            .addQueryParameter(QUERY_PARAMETER_API_KEY, publicKey)
            .addQueryParameter(QUERY_PARAMETER_HASH, hash)
            .build()
        return chain.proceed(
            request.newBuilder()
                .url(newUrl)
                .build()
        )
    }

    private fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }

    companion object {
        private const val QUERY_PARAMETER_TS = "ts"
        private const val QUERY_PARAMETER_API_KEY = "apikey"
        private const val QUERY_PARAMETER_HASH = "hash"
    }
}