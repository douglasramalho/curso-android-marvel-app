package com.example.marvelapp.framework.di

import com.example.marvelapp.framework.network.interceptor.AuthorizationInterceptor
import com.example.marvelapp.BuildConfig
import com.example.marvelapp.framework.network.MarvelApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


// para informar que eh um module
// um module e um carinha que sabe criar dependencias.
//vamos ensinar o dagger como criar as dependencias pra gente
//InstallIn significa aonde vamos instalar esse network module
// em qual scopo ele vai estar disponivel
// queremos que esse network module fique disponivel para toda a aplicacao
// quem for chamar vai receber uma instancia unica dele
// nao vai ser criada novas instancias
// como eu tenho endpoints definidos
// eu nao quero criar uma instancia do retrofit nova pra cada tela que vai chamar um endpoint


// as vezes um tipo nao pode ser injetato pelo construtor. Isso pode ocorrer por varios motivos.
// Por exemplo, nao e possivel injetar uma interface pelo construtor.
// tambem nao e possivel inetar um tipo que nao e seu com o construtor, como uma classe de biblioteca externa(Retrofit)
// nao da pra por um inject no retrofit
// modulo me ajuda a criar instancias de classes ou libs que nao sao meus

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIMEOUT_SECONDS = 15L

    // sabemos que tudo o que for ficar dentro de network sao coisas que vao ser utilizadas por toda
    // nossa aplicacao
    // nao apenas por uma activity ou fragment e sim todos os fragmentos e activities
    // pois todos os fragmentos vao fazer chamadas as apis
    // tudo precisa ser disponibilizado como singleton
    // vai ficar disponivel para toda aplicacao
    // qualquer fragmento que precisar de alguma dependencia nao precisara criar a dependencia
    // pq ela ja foi instanciada

    // pq estamos utilizando o provides mesmo?
    // criar, ensinar o hilt como criar classes que nao sao nossas.
    // nessa caso utilizamos o provides
    // GsonConverterFactory converte automaticamente objetos em json em classes kotlin

    // HttpLoggingInterceptor nos ajuda a debugar a nossa camada de network
    // ele mostra no logcat todas as requests e responses que estao sendo feitas atraves do nosso
    // aplicativo

    // estamos ensinando ao dagger hilt a toda vez ele precisar de uma instancia do HttpLoggingInterceptor
    // ele saber como montar essa instancia
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            // apply aqui usado pra entrar dentro do contexto desse objeto
            setLevel(
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else HttpLoggingInterceptor.Level.NONE
                // se eu nao fornecer essa verificacao aqui mesmo se meu projeto tiver com a build de release
                // store, qualquer um que pegar minha aplicacao e tentar fazer uma engenharia reversa
                // vai conseguir verificar no logcat do android Studio todas as request que estao sendo feitas
                // se o build atual for um build de debug ou seja o build que estou trabalhando
                // eu habilito o log do BODY tudo o que for request ou url ou corpo da request ele vai mostrar
                // pra gente o logCat, e se for qualquer outro tipo de build type, ele vai esconder pra mim
                // qualquer tipo de log do logcat
            )
        }
    }

    @Provides
    fun providesAuthorizationInterceptor(): AuthorizationInterceptor{
        return AuthorizationInterceptor(
            publicKey = BuildConfig.PUBLIC_KEY,
            privateKey = BuildConfig.PRIVATE_KEY,
            calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        )
    }


    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authorizationInterceptor: AuthorizationInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
                // temos um interceptador que vai dar o log de todas as request
            .addInterceptor(loggingInterceptor)
                // temos um interceptador que vai pegar a request e alterar cada uma para colocar o hash
                // pedido na doc da marvel
            .addInterceptor(authorizationInterceptor)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    // tudo aqui sao classes que nao sao minhas ou seja de outros frameworks ou libs
    // nao conheco a implementacao dessas classes.
    // e pra isso utilizamos o provides
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory{ return GsonConverterFactory.create() }

    // como ele sabe instanciar o okHttpClient
    // ele ve que tem uma funcao provides que fornece um retorno okHttpClient
    // ele confere atraves dos tipos
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): MarvelApi {
        // aqu estou injetando pra quem precisa esse MarvelAPi
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(MarvelApi::class.java)
    }

}