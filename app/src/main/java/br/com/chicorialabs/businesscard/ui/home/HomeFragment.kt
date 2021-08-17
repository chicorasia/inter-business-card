package br.com.chicorialabs.businesscard.ui.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.chicorialabs.businesscard.R
import br.com.chicorialabs.businesscard.data.BusinessCard
import br.com.chicorialabs.businesscard.databinding.HomeFragmentBinding
import br.com.chicorialabs.businesscard.ui.adapter.BusinessCardAdapter
import br.com.chicorialabs.businesscard.ui.adapter.BusinessCardListener
import br.com.chicorialabs.businesscard.util.Image
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
     * Configura os comportamentos (métodos callback) do objeto BusinessCardListener,
     * passando os respectivos parâmetros.
     */
    private fun initBusinessCardList() {
        mHomeViewModel.listBusinessCard.observe(viewLifecycleOwner) {
            val adapter = BusinessCardAdapter(BusinessCardListener(clickListener = { id ->
                Toast.makeText(context, "Clicou no id: $id", Toast.LENGTH_SHORT).show()
            }, longClickListener = { businessCard ->
                showRemoveDialog(businessCard)
                true
            }, shareBtnClickListener = {
                context?.let { context -> Image.share(context, it) }
            }
            ))
            binding.homeRecyclerView.layoutManager = LinearLayoutManager(context)
            binding.homeRecyclerView.adapter = adapter
            adapter.submitList(it)
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
                navigateToAddCardFragment()
                mHomeViewModel.doneNavigateToAddCardFragment()
            }
        }
    }

    /**
     * Exibe um Dialog de confirmação antes de apagar um cartão da lista.
     */
    private fun showRemoveDialog(businessCard: BusinessCard) {
        AlertDialog.Builder(activity)
            .setTitle(getString(R.string.dialog_title_delete))
            .setMessage(getString(R.string.dialog_delete_msg))
            .setNegativeButton(getString(R.string.label_no), null)
            .setPositiveButton(getString(R.string.label_yes),
                DialogInterface.OnClickListener { _, _ ->
                mHomeViewModel.remove(businessCard)
            })
            .create()
            .show()
    }

    /**
     * Estou usando a biblioteca Navigation e o plugin safe-args para criar o grafo de
     * navegação e as actions / directions entre os Fragments. Esse método aciona o navController
     * e navega para o AddCardFragment.
     */
    private fun navigateToAddCardFragment() {
        val direction = HomeFragmentDirections.navegaParaAdicionaCartao()
        findNavController().navigate(direction)
    }


}