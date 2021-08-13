package br.com.chicorialabs.businesscard.ui.home

import android.view.View
import androidx.lifecycle.*
import br.com.chicorialabs.businesscard.data.BusinessCard
import br.com.chicorialabs.businesscard.data.BusinessCardRepository
import kotlinx.coroutines.launch


/**
 * Essa classe vai manter as regras de negócio e o acesso
 * ao repositório. A UI do HomeFragment é manipulada a partir das
 * mudanças nos campos do ViewModel.
 */
class HomeViewModel(
    businessCardRepository: BusinessCardRepository
) : ViewModel() {

    /**
     * Acesso à lista de BusinessCard salvos na database por meio da propriedade
     * do BusinessCardRepository. Com essa estrutura o Fragmento não tem acesso
     * direto ao Repository - todas as transações passam pelo ViewModel.
     * Esse campo não é acessível diretamente pelo Fragment ou Activity.
     */
    private val _listBusinessCard: LiveData<List<BusinessCard>> =
        businessCardRepository.listBusinessCard

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
     * Grava alguns cartões no repositório apenas para teste. Limpar os dados
     * usando Tools > ADB Idea > Clear App Data. Eliminar esse bloco de código depois
     * que a gravação a partir do fragment tenha sido implementada.
     */
    init {
        viewModelScope.launch {
            businessCardRepository.save(
                BusinessCard(
                    nome = "Han Solo",
                    empresa = "Outer Rim Transport LLC",
                    email = "han@milleniumfalcon.org",
                    telefone = "212-2222-3333",
                    cardColor = "#32A586"
                )
            )
            businessCardRepository.save(
                BusinessCard(
                    nome = "Orson Crennik",
                    empresa = "Advanced Weapons Research Division",
                    email = "crennik@awrd.empire.org",
                    telefone = "333-2222-1111",
                    cardColor = "#A67F38"
                )
            )
            businessCardRepository.save(
                BusinessCard(
                    nome = "Galen Erso",
                    empresa = "Weapons System Engineer",
                    email = "g_erso@galaticmail.com",
                    telefone = "444-3333-5555",
                    cardColor = "#DC6761"
                )
            )
            businessCardRepository.save(
                BusinessCard(
                    nome = "Mandalorian",
                    empresa = "Bounty Hunters Guild",
                    email = "mando@theguild.org",
                    telefone = "676-7878-8787",
                    cardColor = "#0B87BD"
                )
            )
            businessCardRepository.save(
                BusinessCard(
                    nome = "Lando Calrissian",
                    empresa = "Cloud City",
                    email = "lando@cloudcity.gov",
                    telefone = "111-2222-4444",
                    cardColor = "#C2549C"
                )
            )
            businessCardRepository.save(
                BusinessCard(
                    nome = "Wilhuff Tarkin",
                    empresa = "Death Star Operations Division",
                    email = "grand_moff@dsod.empire.org",
                    telefone = "999-8888-9999",
                    cardColor = "#9BA4B7"
                )
            )
        }
    }
}