package com.example.marvelapp.presentation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import br.com.dio.core.domain.model.Character
import com.example.marvelapp.databinding.FragmentCharactersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
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
    private val characterAdapter = CharactersAdapter()

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

        // como o nosso charactersPagingData retorna um flow de pagingData
        // eu consigo escutar aqui atraves da funcao collect

        lifecycleScope.launch{
            viewModel.charactersPagingData("").collect { pagingData ->
                // collect
                // vai dar um erro que ele espera que esse carinha precisa ser chamao dentro de um scopo de coroutines.
                // o Paging Source, adaptador espera do paging
                characterAdapter.submitData(pagingData)

            }
        }
    }
//    setHasFixedSize(true) torna o meu recyclerView mais performático
    // pq eu sei que all itens vao ter o mesmo tamanho altura e largura fixa e nem quebrar ou aumentar
    // o tamanho do nosso layout


    private fun initCharactersAdapter() {
        with(binding.recyclerCharacters) {
            setHasFixedSize(true)
            adapter = characterAdapter

        }
    }

}