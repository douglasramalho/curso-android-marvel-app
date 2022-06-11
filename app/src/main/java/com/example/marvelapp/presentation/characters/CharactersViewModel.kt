package com.example.marvelapp.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.dio.core.domain.model.Character
import br.com.dio.core.usecase.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// o viewModel sempre vai receber um caso de uso
// a hierarquia fica a seguinte
// view depende do viewModel, viewModel do caso de uso
// caso de uso depende do repository, repository dataSource seja ele remoto ou seja ele local
// e o data source vai fazer a chamada a api ou pegar algum dado do banco
// ensinar o dagger hilt como ele deve instanciar essa classe
// pra ele criar esse carinha com base no ciclo de vida do android
// essa anotacao @HiltViewModel ajuda
// com hiltViewModel ele vai conseguir gerenciar o ciclo de vida corretamente
// ele vai criar aquele factory que tivemos que criar na mao
// aquele factory que instanciamos no nosso fragmento
// faz com que o viewModel guarde os dados corretamente com o ciclo de vida
// e nao ter leak de memoria
// ou seja esse @HiltViewModel vai substituir aquele factory pra gente
@HiltViewModel
class CharactersViewModel @Inject constructor(
    // aqui vamos fazer a nossa primeira melhoria de codigo
    // refatorar ele pra tornar ele mais seguro, mais eficiente.
    // fazendo com que o meu viewModel nao dependa de uma implementacao
    // e sim de uma interface
    private val getCharactersUseCase: GetCharactersUseCase
    // se minha tela(activity ou fragment) de personagens precisar fazer outra coisa
    // alem de obter personagens eu simplesmente comeco a receber casos de uso.
    // deixando assim minha aplicacao muito enxuta
    // assim cada caso de uso com sua responsabilidade muito bem definida
    // assim chamamos qual funcao queremos atravez de cada caso de uso
    // temos aqui responsabilidades de regras da nossa aplicacao quebradas dentro dos casos de uso
) : ViewModel() {


    // vou ta passando aqui o query at chegar na camada de rede
    // que vai ser a pesquisa do usuario
    fun charactersPagingData(query: String): Flow<PagingData<Character>> {
        // como o nosso getCharacterUseCase tem essa funcao dentro(PagingUseCase) aqui invoke
        // eu posso chamar sem o invoke
        return getCharactersUseCase(
            GetCharactersUseCase.GetCharactersParams(query, getPageConfig())
        // deixando mais perfomatico meu viewModel
        ).cachedIn(viewModelScope)
    }

    private fun getPageConfig() = PagingConfig(
        // dps vamos extrair esse 20 pra uma constante pq tambem utilizamos ele no repository
        pageSize = 20
    )

}