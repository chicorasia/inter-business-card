package br.com.chicorialabs.businesscard.ui.home

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import br.com.chicorialabs.businesscard.data.BusinessCard


/**
 * Essa classe vai manter as regras de negócio e o acesso
 * ao repositório. A UI do HomeFragment é manipulada a partir das
 * mudanças nos campos do ViewModel.
 */
class HomeViewModel : ViewModel() {

    /**
     * Um objeto MutableLiveData<List<BusinessCard> provisório. Depois será substituído por
     * uma referência ao LiveData do Repository.
     */
    private val listBusinessCard = MutableLiveData<List<BusinessCard>>()

    /**
     * Esse campo verifica se a lista de BusinessCard está vazia. Se estiver, retorna View.VISIBLE
     * para fazer com que o TextView de lista vazia fique visível. Se estiver cheia, retorna
     * View.GONE para ocultar o TextView e mostrar somente a RecyclerView.
     */

    val showEmptyListMessage = Transformations.map(listBusinessCard) {
        if (it.isEmpty()) {
            return@map View.VISIBLE
        }
        else return@map View.GONE
    }

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