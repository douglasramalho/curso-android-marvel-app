package com.example.marvelapp.framework.netowork.interceptor

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
        // vamos criar a logica para encapsular nossas request como a api da marvel pede
        // e nao precisa jogar essa hash em toda request na interface do retrofit
        // atraves da chain consigo ter acesso a algumas coisas da minha request
        // quando uma coisa nova for feita ele vai cair no intercept
        // e vamos atribuir nessa variavel request
        val request = chain.request()
        val requestUrl = request.url

        // precisamos passar o timestamp em segundos e nao em mili
        // vou dividir por mil e colocar um l pois e long
        val timestamp = (calendar.timeInMillis / 1000L).toString() // time in second
        val hash = "$timestamp$privateKey$publicKey".toMd5()
        // vou pegar a url atual e vou modificar vou adiconar
        // novos queries parameter mais os carinhas que ja estavamos utilizando
        // private key nao e passada
        // ela so e usada para montar o nosso hash
        // nao importa qual endpoint for chamado
        // vamos inteceptar e adicionar esse carinha novo aqui
        // poupando nosso trabalho de fazer isso pra cada request

        val newUrl = requestUrl.newBuilder()
            .addQueryParameter(QUERY_PARAMETER_TIME_STAMP, timestamp)
            .addQueryParameter(QUERY_PARAMETER_API_KEY, publicKey)
            .addQueryParameter(QUERY_PARAMETER_HASH, hash)
            .build()

        // vou pedir pra ela prosseguir com a request
        // quero continuar com a mesma request
        // nao quero cancelar
        // nem mudar minha request
        // so quero trocar mesmo o valor da url
        return chain.proceed(
            request.newBuilder()
                .url(newUrl)
                .build()
        )
    }

    // precisamos converter para md5
    // transforma o valor que eu recebi numa chave hash md5

    // vc nao precisa criar const para coisas que vc nao sabe definir
    @Suppress("MagicNumber")
    private fun String.toMd5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }

    companion object {
        private const val QUERY_PARAMETER_TIME_STAMP = "ts"
        private const val QUERY_PARAMETER_API_KEY = "apikey"
        private const val QUERY_PARAMETER_HASH = "hash"
    }
}