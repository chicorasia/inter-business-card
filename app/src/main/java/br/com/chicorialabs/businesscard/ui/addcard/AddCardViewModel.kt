package br.com.chicorialabs.businesscard.ui.addcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.chicorialabs.businesscard.data.BusinessCard
import br.com.chicorialabs.businesscard.data.BusinessCardRepository
import kotlinx.coroutines.launch

/**
 * Essa classe dá suporte ao fragmento de Adicionar Cartão
 */
class AddCardViewModel(private val businessCardRepository: BusinessCardRepository) : ViewModel() {

    /**
     * Essa variável representa o BusinessCard que será criado. Ela é iniciada com valor nulo e,
     * quando o usuário clica no botão para salvar, ela recebe o novo Cartão. O Fragment
     * observa essa variável e, quando não é nula, retira o AddCardFragment da stack e volta para a
     * tela inicial.
     */
    private val _newCard = MutableLiveData<BusinessCard>().apply { value = null }
    val newCard: LiveData<BusinessCard>
        get() = _newCard

    /**
     * Esses campos servem para fazer o DataBinding bidirecional com os componentes EditText
     * do layout. Eles são inicializados com strings vazias, mas no momento da criação
     * do BusinessCard é aplicada uma regra de validação.
     */
    val nomeField = MutableLiveData<String>("")
    val telefoneField = MutableLiveData<String>("")
    val empresaField = MutableLiveData<String>("")
    val emailField = MutableLiveData<String>("")
    val cardColorField = MutableLiveData<String>("#FF32A586")

    fun createNewCard() {
        val nome = nomeField.value.toString()
        if (nomeIsValid(nome)) {
            _newCard.value = BusinessCard(
                nome = nome,
                empresa = empresaField.value.toString(),
                telefone = telefoneField.value.toString(),
                email = emailField.value.toString(),
                cardColor = cardColorField.value.toString()
            )
            saveNewCard()
        }
//        TODO: adicionar uma mensagem de erro de validação do nome
    }

    /**
     * O nome do contato deve ter pelo menos 3 caracteres. Os outros campos podem ser vazios.
     */
    private fun nomeIsValid(nome: String?): Boolean = !nome.isNullOrEmpty() && nome.length >= 3

    /**
     * Define a cor do card
     */
    fun setCardColor(color: String) {
        cardColorField.value = color
    }

    /**
     * Vai salvar o BusinessCard no repositório somente quando o valor não for nulo - ou seja,
     * depois que um cartão tenha sido criado.
     */
    private fun saveNewCard() {
        newCard.value?.let {
            viewModelScope.launch {
                businessCardRepository.save(it)
            }
        }
        doneNavigateToHomeFragment()
    }

    fun doneNavigateToHomeFragment() {
        _newCard.value = null
    }
}