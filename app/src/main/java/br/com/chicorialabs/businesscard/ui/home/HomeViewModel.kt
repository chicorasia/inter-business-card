package br.com.chicorialabs.businesscard.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


/**
 * Essa classe vai manter as regras de negócio e o acesso
 * ao repositório. A UI do HomeFragment é manipulada a partir das
 * mudanças nos campos do ViewModel.
 */
class HomeViewModel : ViewModel() {

    /**
     * Esse campo é observado pelo HomeFragment; quando o valor é true se dispara
     * a navegação para o AddCardFragment.
     */
    val navigateToAddCardFragment = MutableLiveData<Boolean>(false)

    /**
     * O primeiro método seta o campo navigateToAddCardFragment e dispara a navegação;
     * o segundo método reseta o campo depois que a navegação foi concluída.
     */

    fun navigateToAddCardFragment() : Unit {
        navigateToAddCardFragment.value = true
    }

    fun doneNavigateToAddCardFragment() : Unit {
        navigateToAddCardFragment.value = false
    }

}