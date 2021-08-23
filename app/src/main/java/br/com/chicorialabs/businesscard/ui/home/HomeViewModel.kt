package br.com.chicorialabs.businesscard.ui.home

import android.view.View
import androidx.lifecycle.*
import br.com.chicorialabs.businesscard.domain.BusinessCard
import br.com.chicorialabs.businesscard.extension.sortedByName
import br.com.chicorialabs.businesscard.usecase.ApplySearchFilterUseCase
import br.com.chicorialabs.businesscard.usecase.ReadFromDatabaseUseCase
import br.com.chicorialabs.businesscard.usecase.RemoveFromDatabaseUseCase
import kotlinx.coroutines.launch


/**
 * Essa classe vai manter as regras de negócio e o acesso
 * ao repositório. A UI do HomeFragment é manipulada a partir das
 * mudanças nos campos do ViewModel.
 */
class HomeViewModel(
    readFromDatabaseUseCase: ReadFromDatabaseUseCase,
    private val removeFromDatabaseUseCase: RemoveFromDatabaseUseCase,
    applySearchFilterUseCase: ApplySearchFilterUseCase,

) : ViewModel() {

    /**
     * Acesso à lista de BusinessCard salvos na database por meio da propriedade
     * do BusinessCardRepository. Com essa estrutura o Fragmento não tem acesso
     * direto ao Repository - todas as transações passam pelo ViewModel.
     * Esse campo não é acessível diretamente pelo Fragment ou Activity.
     */
    private val _listBusinessCard: LiveData<List<BusinessCard>> =
        readFromDatabaseUseCase.listBusinessCard

    /**
     * Esse campo fica exposto para o Fragment.
     */
    val listBusinessCard: LiveData<List<BusinessCard>>
        get() = _listBusinessCard

    /**
     * Esse campo verifica se a lista de BusinessCard está vazia. Se estiver, retorna View.VISIBLE
     * para fazer com que o TextView de lista vazia fique visível. Se estiver cheia, retorna
     * View.GONE para ocultar o TextView e mostrar somente a RecyclerView.
     */

    val showEmptyListMessage = Transformations.map(_listBusinessCard) {
        if (it.isEmpty()) {
            return@map View.VISIBLE
        } else return@map View.GONE
    }

    /**
     * Esse campo representa a query de busca. Como esse campo é manipulado
     * por um método setter ele pode ser privado.
     */

    private val _searchQuery = MutableLiveData<CharSequence>("")
    val searchQuery: LiveData<CharSequence>
        get() = _searchQuery

    /**
     * Um método para modificar a _searchQuery
     */
    fun setSearchQuery(query: CharSequence?) {
        query?.let {
            _searchQuery.value = it
        }
    }

    /**
     * A lista filtrada é exposta para o Fragment. Como o filtro é aplicado
     * dinamicamente e gera uma nova lista, os dados do repositório não
     * são modificados. Ao final é invocada uma função de extensão para
     * ordenar a lista em ordem alfabética.
     */
    val filteredListBusinessCard: LiveData<List<BusinessCard>> =
        applySearchFilterUseCase.filterList(listBusinessCard, searchQuery).sortedByName()


    /**
     * Esse campo é observado pelo HomeFragment; quando o valor é true se dispara
     * a navegação para o AddCardFragment.
     */
    val navigateToAddCardFragment = MutableLiveData<Boolean>(false)

    /**
     * O primeiro método seta o campo navigateToAddCardFragment e dispara a navegação;
     * o segundo método reseta o campo depois que a navegação foi concluída.
     */

    fun navigateToAddCardFragment(): Unit {
        navigateToAddCardFragment.value = true
    }

    fun doneNavigateToAddCardFragment(): Unit {
        navigateToAddCardFragment.value = false
    }

    /**
     * Remove um item do repositório.
     */
    fun remove(businessCard: BusinessCard) {
        viewModelScope.launch {
            removeFromDatabaseUseCase.remove(businessCard)
        }
    }
}