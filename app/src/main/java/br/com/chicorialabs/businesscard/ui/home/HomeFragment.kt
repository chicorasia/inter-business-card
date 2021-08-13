package br.com.chicorialabs.businesscard.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.chicorialabs.businesscard.databinding.HomeFragmentBinding
import br.com.chicorialabs.businesscard.ui.adapter.BusinessCardAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Este é o fragmento da tela principal / home; o ViewModel é instanciado por meio da
 * biblioteca Koin. A manipulação da UI é feita sempre que possível por meio de
 * campos observáveis no ViewModel. Optei por usar Listener Bindings para capturar os cliques
 * nos botões e outros objetos para reduzir o acoplamento do código.
 */
class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val mHomeViewModel: HomeViewModel by viewModel()
    private val binding: HomeFragmentBinding by lazy {
        HomeFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * Atribui o mHomeViewModel ao campo viewModel do binding; define o lifecycleowner
         * do binding para que os Listener Bindings funcionem corretamente.
         */
        binding.viewModel = mHomeViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initBusinessCardList()
        initNavigateToAddCardObserver()
        return binding.root
    }

    /**
     * Observa a lista armazenada no ViewModel, instancia o Adapter e inicializa
     * a RecyclerView com um layout linear.
     */
    private fun initBusinessCardList() {
        mHomeViewModel.listBusinessCard.observe(viewLifecycleOwner) {
            val adapter = BusinessCardAdapter()
            adapter.submitList(it)
            binding.homeRecyclerView.layoutManager = LinearLayoutManager(context)
            binding.homeRecyclerView.adapter = adapter
        }
    }

    /**
     * Esse método configura o observer para o parâmetro navigateToAddCardFragment;
     * quando o campo tem valor true, ele dispara a navegação para o AddCardFragment
     * e imediatamente chama o método que reseta o valor do campo.
     */
    private fun initNavigateToAddCardObserver() {
        mHomeViewModel.navigateToAddCardFragment.observe(viewLifecycleOwner) { navigateToAddCardFragment ->
            if (navigateToAddCardFragment == true) {
                navegaParaAddCardFragment()
                mHomeViewModel.doneNavigateToAddCardFragment()
            }
        }
    }

    /**
     * Estou usando a biblioteca Navigation e o plugin safe-args para criar o grafo de
     * navegação e as actions / directions entre os Fragments. Esse método aciona o navController
     * e navega para o AddCardFragment.
     */
    private fun navegaParaAddCardFragment() {
        val direction = HomeFragmentDirections.navegaParaAdicionaCartao()
        findNavController().navigate(direction)
    }


}