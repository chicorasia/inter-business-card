package br.com.chicorialabs.businesscard.ui.addcard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.chicorialabs.businesscard.databinding.FragmentAddCardBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Esse Fragment vai manter a tela de adição de um novo cartão de visita. Ele ainda
 * O arquivo XML possui um atributo do tipo NavController para que consiga operar
 * diretamente a navegação. Como no HomeFragment, é preciso inicializar o lifecycleOwner
 * do binding para que os listener bindings funcionem corretamente.
 *
 */

class AddCardFragment : Fragment() {

    private val mAddCardViewModel: AddCardViewModel by viewModel()
    private val binding: FragmentAddCardBinding by lazy {
        FragmentAddCardBinding.inflate(layoutInflater)
    }
    private val arguments by navArgs<AddCardFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * Define o campo _receivedCard no ViewModel caso receba um
         * cartão como argumento na navegação, i.e., vai editar um cartão
         * existente ao invés de criar um novo
         */
        arguments.businessCard.let {
            if (it != null) {
                mAddCardViewModel.receiveCard(it)
            }
        }


        /**
         * Fazer a vinculação do NavController com o campo variable definido no
         * arquivo XML; fazer a vinculaçõo do lifeCycleOwner.
         */
        binding.navController = findNavController()
        binding.lifecycleOwner = viewLifecycleOwner

        mAddCardViewModel.errorSnackbar.observe(viewLifecycleOwner) {
            it?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                Log.i("BusinessCard", "snackBar msg: $it")
            }
        }
        /**
         * Fazer a vinculação entre o campo viewModel do layout XML e
         * o ViewModel da atividade. Esse é um detalhe que é fácil de esquecer
         * e sem ele o DataBinding não funciona!
         */
        binding.viewModel = mAddCardViewModel

        initNavigateHomeObserver()
        return binding.root
    }

    /**
     * Observa o valor da variável newCard no ViewModel. Quando um cartão válido é
     * criado esse método dispara a navegação de volta para a tela inicial.
     */
    private fun initNavigateHomeObserver() {
        mAddCardViewModel.newCard.observe(viewLifecycleOwner) {
            if (it != null) {
                navigateToHomeFragment()
            }
        }
    }

    /**
     * Tira o fragment da stack e volta para a tela inicial.
     */
    private fun navigateToHomeFragment() {
        findNavController().popBackStack()
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            AddCardFragment().apply {

            }
    }
}