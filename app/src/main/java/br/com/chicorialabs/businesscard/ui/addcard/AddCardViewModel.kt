package br.com.chicorialabs.businesscard.ui.addcard

import androidx.lifecycle.*
import br.com.chicorialabs.businesscard.data.BusinessCard
import br.com.chicorialabs.businesscard.data.BusinessCardRepository
import kotlinx.coroutines.Dispatchers
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
     * Uma variável que recebe um cartão do HomeFragment quando se trata de edição
     * de um BusinessCard já existente.
     */
    private val _receivedCard = MutableLiveData<BusinessCard>(null)
    val receivedCard: BusinessCard?
        get() = _receivedCard?.value

    /**
     * Essa variável permite modificar o título da tela caso seja uma edição de
     * cartão existente. O boolean é passado por um BindingAdapter para decidir
     * qual string de texto deve ser exibida.
     */
    val showEditLabel = Transformations.map(_receivedCard) {
        it != null
    }

    /**
     * Esses campos servem para fazer o DataBinding bidirecional com os componentes EditText
     * do layout. Eles são inicializados com strings vazias e podem ser alterados no método
     * initFields(), quando é recebido um BusinessCard. No momento da criação do cartão
     * o campo nome passa por um teste de validação.
     */
    val nomeField = MutableLiveData<String>("")
    val telefoneField = MutableLiveData<String>("")
    val empresaField = MutableLiveData<String>("")
    val emailField = MutableLiveData<String>("")
    val cardColorField = MutableLiveData<String>("#FF32A586")


    /**
     * Cria um novo cartão a partir dos valores nos campos EditText.
     */
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
            saveCard()
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
     * Verifica se foi recebido um cartão. Caso positivo, atualiza o cartão no repositório.
     * Senão, grava um novo cartão.
     */
    private fun saveCard() {
        if (receivedCard != null) {
            viewModelScope.launch {
                newCard.value?.let {
                    businessCardRepository.update(
                        it.copy(id = receivedCard?.id!!)
                    )
                }
            }
        } else {
            newCard.value?.let {
                viewModelScope.launch {
                    businessCardRepository.save(it)
                }
            }
        }
        doneNavigateToHomeFragment()
    }

    fun doneNavigateToHomeFragment() {
        _newCard.value = null
    }


    /**
     * Recebe um BusinessCard e atribui à variável _receivedCard
     */
    fun receiveCard(businessCard: BusinessCard) {
        with(businessCard){
            _receivedCard.value = businessCard
            initFields(this)
        }
    }

    /**
     * Inicializa os campos do formulário com os valores do
     * cartão recebido.
     */
    fun initFields(businessCard: BusinessCard) {
        with(businessCard) {
            nomeField.value = nome
            telefoneField.value = telefone
            empresaField.value = empresa
            emailField.value = email
        }
    }

}