package com.example.marvelapp.presentation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.marvelapp.databinding.FragmentCharactersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


// isso permite que o CharactersFragment possa trabalhar com injecao de dependencia
// faz com que esse carinha consiga receber uma dependencia uma coisa injetada em uma variavel
// lembrar que instancia o fragmento e o proprio framework por isso da anotacao
// por isso precisamos receber uma instancia de alguma coisa via propriedade
@AndroidEntryPoint
class CharactersFragment : Fragment() {


    // aprendemos que temos que utilizar esse padrao de back-propeties do kotlin
    // pq assim utilizamos essa variavel de uma maneira muito mais facil
    // eu sei que ele nunca vai ser nulo pq eu vou esta instanciando ele aqui
    // no nosso on Create view
    private var _binding: FragmentCharactersBinding? = null

    // quando eu for pegar esse carinha aqui (binding) eu sei que ele nao vai ser nulo
    // ele nunca vai ser nulo pq eu vou esta instanciando ele no onCreateView
    // e como eu so vou usar esse cara (binding) no meu onViewCreated eu garanto que dps que meu
    // onCreateView for chamado eu garanto que essa variavel esta de fato settada
    // entao eu posso forcar ela aqui  _binding!!
    // pq assim eu nao preciso utilizar o (?)
    // eu nao posso utilizar esse carinha assim
    //    private val binding: FragmentCharactersBinding = _binding!!
    // so quando eu for fazer o get do binding que eu vouq querer esse  _binding!!
    // esse _binding!! aqui so vai ser chamado quando eu chamar esse carinha aqui
    //   with(binding.recyclerCharacters) {
    //            setHasFixedSize(true)
    //            adapter = characterAdapter
    //
    //        }
    // ou seja so vai ser chamado quando eu chamar o primeiro binding que no caso e no initCharacters
    // da forma que estava antes sem o get ja tenta passar uma instancia desse cara _binding!!
    // pra dentro do meu binding e claro  ele vai chamar isso antes do meu onCreateView
    private val binding: FragmentCharactersBinding get() = _binding!!
    private val viewModel: CharactersViewModel by viewModels()
    // isso aqu nao tem nada a ver com hilt e sim com componentes de arquitetura do android
    // nao vamos implementar um factory, mas o dagger hilt sabe atravez do androidEntryPoint
    // e por saber que nosso charactersViewModel tem HiltViewModel
    // ele vai conseguir instanciar um viewModel passando todas as dependencias que ele precisa
    // vai injetar todos os outros componentes que o viewModel precisa
    // e vai entregar pra gente esse viewModel pronto para usar


    // vou instanciar o nosso adapter fora do ciclo de vida dos nosso metodos aqui
    // pois quando eu tiver em outra tela e voltar ele vai chamar o onViewCreated denovo
    // e nao quero instanciar o meu adapter no onViewCreated
    // pq se nao eu vou perder a referencia  pq ele vai criar uma nova instancia
    // vou perder a lista que ta sendo exibida
    // instanciando fora dos meus ciclos de vida garante que quando eu voltar
    // pra essa tela apartir de uma outra tela eu vou ter o meu adapter no mesmo estado
    // nao vou recriar ele novamente
    // estou iniciando esse carinha diretamente dentro do meu frament
    // toda vez que eu coloco o meu aplicativo em background ou chamo uma tela, e volto pra essa tela.
    // ele ia instanciar novamente essa carinha. E dentro dele ia ter um estado que ele ia ficar
    // adicionando novos carinhas dentro desse estado, isso nao eh legal.
    // no lugar de instanciar assim eu vou usar o late init var
    private lateinit var characterAdapter: CharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCharactersBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        // atribuimos aqui a instancia do nosso FragmentCharactersBinding
        // no caso ele espera um root retornado
        // que eh o retorno do nosso onCreateView
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // dentro do onViewCreate eu tenho certeza que esse carinha (_binding) ja foi implementado
        // e se esse ja foi implementado o binding tambem ja foi
        super.onViewCreated(view, savedInstanceState)
        initCharactersAdapter()
        observeInitialLoadState()

        // como o nosso charactersPagingData retorna um flow de pagingData
        // eu consigo escutar aqui atraves da funcao collect

        // existe uma maneira mais segura de observar um estado no fragment usando flow
        // observar o estado do flow apenas dentro do launch
        // ele eh inseguro pq como nosso aplicativo vai pra background
        // esse flow aqui continua escutando
        // se meu aplicativo esta em background
        // e por algum acaso esse pagingData retorna alguma coisa nova
        // enquanto o aplicativo esta em background
        // o nosso flow vai continuar recebendo esses estados, quando ele chamar esse submitData do
        // adapter o adapter nao vai ta visivel no momento e podemos ter um crash em tempo de execucao

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // automaticamente quando nosso aplicativo for pra background
                // ele vai fazer o stop desse flow, ai esse flow vai parar de escutar as atualizacoes
                // e quando o usuario voltar pro aplicativo.
                // e abrir o fragmento de personagens ele vai iniciar a coletar novamente esse fluxo
                // de personagens quando o nosso ciclo de vida do nosso fragmento estiver no on Start
                viewModel.charactersPagingData("").collect { pagingData ->
                    // collect  vai dar um erro que ele espera que esse carinha precisa ser chamao
                    // dentro de um scopo de coroutines.
                    // o Paging Source, adaptador espera do paging
                    characterAdapter.submitData(pagingData)
                }
            }
        }
    }


//    setHasFixedSize(true) torna o meu recyclerView mais performático
    // pq eu sei que all itens vao ter o mesmo tamanho altura e largura fixa e nem quebrar ou aumentar
    // o tamanho do nosso layout


    private fun initCharactersAdapter() {
        characterAdapter = CharactersAdapter()
        with(binding.recyclerCharacters) {
            scrollToPosition(0)
            setHasFixedSize(true)
            // geralmente como estamos fazendo com scroll infinito, o usuario sempre vai conseguir ver mais
            // de baixo pra cima, sempre ver mais eh no final da pagina
            adapter = characterAdapter.withLoadStateFooter(
                footer = CharactersLoadingStateAdapter(
                    characterAdapter::retry
                // perdemos a conexao com a internet e estamos na pagina 3
                // o adapter vai guardar o offsset
                // e assim que clicarmos em retry ele vai tentar exibir o proximo ofsset
                // ele guarda o estado dentro dele.
                // automaticamente ele guarda isso pra gente

                )
            )


        }
    }

    private fun observeInitialLoadState() {
        // o PagingDataAdapter nos fornece um meio de saber que estado esta a tela..
        // assim eu posso observar e reagir
        lifecycleScope.launch {
            characterAdapter.loadStateFlow.collectLatest { loadState ->
                // vamos fazer verificacao desse carinha pra saber que estado ele esta.
                binding.flipperCharacters.displayedChild = when (loadState.refresh) {
                    is LoadState.Loading -> {
                        // estado de loading eu quero mostrar o nosso shimer
                        setShimmerVisibility(true)
                        FLIPPER_CHILD_LOADING

                    }

                    is LoadState.NotLoading -> {
                        setShimmerVisibility(false)
                        FLIPPER_CHILD_CHARACTERS

                    }

                    is LoadState.Error -> {
                        setShimmerVisibility(false)
                        binding.includeViewCharactersErrorState.buttonRetry.setOnClickListener {
                            // eu teria que chamar novamente a request
                            // o proprio page adapter tem uma funcao de refresh pra mim
                            // o nosso Adapter tem o estado e sabe qual que a pagina atual
                            // qual que eh a proxima pagina
                            // como ele ja controla tudo isso internamente
                            // ele sabe que nao conseguimos carregar esse carinha com sucesso
                            // quando tiver um erro ele vai guardar a pagina que nao foi carregada
                            // e quando chamar o refresh ele vai fazer o refresh daquela pagina
                            // o refresh faz e carregar todos os dados
                            // primeiro ele limpa tudo e dps ele faz a request no indice 0
                            // que e o nosso offsset
                            characterAdapter.refresh()
                        }
                        FLIPPER_CHILD_ERROR

                    }
                }
            }
        }
    }

    private fun setShimmerVisibility(visibility: Boolean) {
        binding.includeViewCharactersLoadingState.shimmerCharacters.run {
            // entrei dentro dele.
            isVisible = visibility
            // funcao de extensao da biblioteca android x
            // ao inves de eu setar visibilidade para a view
            // ao inves de eu chamar uma visibilidade em uma view passando view.gone ou view.visible
            // eu posso simplesmente chamar o isVisible
            // entao eu passo o nosso visibility
            // entao eu passo true aqui a minha view vai esta visivel
            // se eu passo false a minha view vai esta escondida
            if (visibility)
                startShimmer()
            else stopShimmer()
        }
    }

    companion object {
        private const val FLIPPER_CHILD_LOADING = 0
        private const val FLIPPER_CHILD_CHARACTERS = 1
        private const val FLIPPER_CHILD_ERROR = 2
    }

}