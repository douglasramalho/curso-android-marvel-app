package br.com.dio.core.usecase.base

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

// trabalhar com dois tipo genericos basicamente isso sera nosso paramentros
// p entrada
// R saida
// a camada de apresentacao pode passar um nome, email ou password.
// ou seja os dados que o usuario digitar na tela serao passados pro nosso userCase
// dentro de um objetinho chamado parents
// all user case vai precisar retornar algo pra camada de apresentacao
// ate mesmo se o use case so executar alguma coisa e nao retornar nada
// vamos definir aqui o tipo de retorno
// tem que ser do tipo UNIT
// que vai representar o retorno do tipo vazio
// basicamente vai ser um classe abstrata
// que quem implementar essa classe abstrata
// vai receber um parametro e vai retornar um alguma coisa
// como eu falei o nosso caso de uso so executa uma coisa
// ou seja ele tem aquele conceito do SOLID de responsabilidade unica
// muito bem definido nele mesmo

abstract class UserCase<in P, R>{
    // utilizando o operator
    // garanto que quem chamar meu useCase
    // ele pode chamar o use case instanciando, automaticamente ele vai chamar essa funcao invoke
    // ele vai receber um params e que vai ser um p
    // que eh o tipo de entrada que eu defini aqui
    // vou chamar um bloco pq ele vai criar um fluxo de dados
    //
    // eh como se fosse um builder ne um construtor de fluxo
    // aqui do couroutine
    operator fun invoke(params: P): Flow<ResultStatus<R>> = flow {
        // vou chamar um emit, emit e uma funcao que eu ganhei por esta dentro do flow
        // toda tela que nos vamos trabalhar vai fazer uma request
        // e toda vez que eu faco uma request eu tenho que exibir um loading pro usuario
        // dps que eu exibir o loading, ou exibo um erro ou exibo um sucesso
        // entao vou comecar a emitir esses dados aqui atravez de um flow
        // quem chamar esse invoke aqui vai abrir um fluxo de dados
        // vai ficar escutando pro esse fluxo
        // e cada vez que eu chamo o emit
        // quem tiver escutando esse flow vai receber o dado dentro do emit
        // o dado vai ta sendo emitido dentro do emit
        // e vai reagir de acordo com esse dado que esta sendo passado
        // qual e fluxo?
        // sempre que eu fizer invoke vai ser loading
        // dps ou vai ser sucesso ou erro
        // como eu disse que o retorno do nosso flow
        // e do tipo resultStatus
        // o que eu vou fazer?
        // quando eu chamar o nosso invoke
        // vou chamar o ResultStatus
        // como o retorno do loading e nothing eu aqui ja estou emitindo um loading
        // pra que esta escutando

        emit(ResultStatus.Loading)
        // dps do loadind ele vai chamar a funcao que deve executar
        // quem implementar esse use case vai implementar esse do work
        emit(doWork(params))
    }.catch { throwable ->
        // consigo recuperar qualquer erro que ocorrer dentro do flow
        // vamos supoer que dentro do do work ocorra algum erro
        // estou fazendo uma request pra minha api e ocorreu um erro nessa request
        emit(ResultStatus.Error(throwable))
    }
    protected abstract suspend fun doWork(params: P): ResultStatus<R>
    // retornarmos o sucesso pra que estiver utilizando
}

abstract class PagingUseCase<in P, R: Any>{

    // use case para pagination
    // pode definir variosu use case para trabalhar com varios tipos que vc quiser
    // pq eu estou retornando um pagingData?
    // pq eu sei que minha biblioteca ela retorna um pagingData para minha camada de view
    operator fun invoke(params: P) : Flow<PagingData<R>> = createFlowObservable(params)

    // o que tem embaixo e praticamente o que tem em cima
    // sem esse lance de mostrar o loading...
    // eu sei que essa biblioteca do paging vai me fornecer como se fosse o ResultStatus
    // ai vou saber se vai ta sendo Loading, se vai ta sendo sucesso ou erro
    protected abstract fun createFlowObservable(params: P): Flow<PagingData<R>>


}