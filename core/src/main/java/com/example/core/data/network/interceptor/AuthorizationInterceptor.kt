package com.example.core.data.network.interceptor


import okhttp3.Interceptor
import okhttp3.Response
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

class AuthorizationInterceptor(
    private val publicKey: String,
    private val privateKey: String,
    private val calendar: Calendar
) : Interceptor {

    @Suppress("MagicNumber")
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestUrl = request.url

        val timesTamp = (calendar.timeInMillis / 1000L).toString() //time in seconds
        val hash = "$timesTamp$privateKey$publicKey".md5()
        val newUrl = requestUrl.newBuilder()
            .addQueryParameter(QUERY_PARAMETER_TS, timesTamp)
            .addQueryParameter(QUERY_PARAMETER_API_KEY, publicKey)
            .addQueryParameter(QUERY_PARAMETER_HASH, hash)
            .build()

        return chain.proceed(
            request.newBuilder()
                .url(newUrl)
                .build()
        )
    }

    @Suppress("MagicNumber")
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

/*
*https://developer.marvel.com/documentation/authorization
* Authentication for Server-Side Applications
* Server-side applications must pass two parameters in addition to the apikey parameter:
* ts - a timestamp (or other long string which can change on a request-by-request basis)
* hash - a md5 digest of the ts parameter,
* your private key and your public key (e.g. md5(ts+privateKey+publicKey)
* For example, a user with a public key of "1234" and a private key of "abcd" could construct
* a valid call as follows:
* http://gateway.marvel.com/v1/public/comics?ts=1&apikey=1234&hash=ffd275c5130566a2916217b101f26150
* (the hash value is the md5 digest of 1abcd1234)
*
 */