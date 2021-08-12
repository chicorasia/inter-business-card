package br.com.chicorialabs.businesscard.ui.addcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.chicorialabs.businesscard.R
import br.com.chicorialabs.businesscard.databinding.FragmentAddCardBinding


/**
 * Esse Fragment vai manter a tela de adição de um novo cartão de visita. Ele ainda
 * O arquivo XML possui um atributo do tipo NavController para que consiga operar
 * diretamente a navegação. Como no HomeFragment, é preciso inicializar o lifecycleOwner
 * do binding para que os listener bindings funcionem corretamente.
 *
 */

class AddCardFragment : Fragment() {

    private val binding: FragmentAddCardBinding by lazy {
        FragmentAddCardBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * Fazer a vinculação do NavController com o campo variable definido no
         * arquivo XML; fazer a vinculaçõo do lifeCycleOwner.
         */
        binding.navController = findNavController()
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
     }

    companion object {

        @JvmStatic
        fun newInstance() =
            AddCardFragment().apply {

            }
    }
}