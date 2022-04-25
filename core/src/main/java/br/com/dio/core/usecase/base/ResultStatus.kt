package br.com.dio.core.usecase.base

sealed class ResultStatus<out T> {
    // sealed class que serve para representar
    // os estados das nossas requisicoes, das nossas acoes
    // quem for usar esse ResultStatus vai determinar que tipo de dado ele usara
    // temos aqui o loading que implementa o ResultStatus
    // no Loading nao queremos se preocupar com tipo de dados que queremos retornar
    // entao colocamos Loading como Nothing
    // quando nossa api ou camada de dados retornar algo com sucesso
    //o que eu estou fazendo aqui eh passando uma variavel data success
    // que vai ser do tipo T que vamos trabalhar em determinado caso de uso
    // data class do tipo error vai implementar um Throwable como o Throwable nao retorna um sucesso
    // nao retorna um dado que eu quero eu passo que o nosso generico vai ser do tipo Nothing


    object Loading : ResultStatus<Nothing>()
    data class Success<out T>(val data: T) : ResultStatus<T>()
    data class Error(val throwable: Throwable) : ResultStatus<Nothing>()

    override fun toString(): String {
        // pra caso queiramos fazer um result String no nosso status
        return when (this) {
            Loading -> "Loading"
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[throwable=$throwable"
        }

    }
    // e muito utilizado em aplicacoes em producao
    // facilita muito nossa vida
    // para podermos trabalhar com os estados
    // desse meio de campo
    // entre a camada de apresentacao
    // e a camada de dados
    //
}
