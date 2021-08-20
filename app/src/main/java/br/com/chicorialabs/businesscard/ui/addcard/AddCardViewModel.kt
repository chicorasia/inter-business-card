package br.com.chicorialabs.businesscard.ui.addcard

import androidx.lifecycle.*
import br.com.chicorialabs.businesscard.domain.BusinessCard
import br.com.chicorialabs.businesscard.domain.BusinessCardValidation
import br.com.chicorialabs.businesscard.domain.Either
import br.com.chicorialabs.businesscard.usecase.SaveToDatabaseUseCase
import kotlinx.coroutines.launch

/**
 * Essa classe dá suporte ao fragmento de Adicionar Cartão. O acesso para gravação na
 * database acontece por meio do atributo saveToDatabaseUseCase. Com isso, o
 * ViewModel não mantém mais nenhuma referência ao repository. De fato, esse ViewModel
 * lida somente com os aspectos visuais e de coletar as informações do usuário.
 * A validação e o salvamento foram delegados para classes específicas.
 */
class AddCardViewModel(
    private val saveToDatabaseUseCase: SaveToDatabaseUseCase
) : ViewModel() {


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
        get() = _receivedCard.value

    /**
     * Essa variável permite modificar o título da tela caso seja uma edição de
     * cartão existente. O boolean é passado por um BindingAdapter para decidir
     * qual string de texto deve ser exibida.
     */
    val showEditLabel = Transformations.map(_receivedCard) {
        it != null
    }

    /**
     * Esse campo é usado para mostrar erros na forma de Snackbar.
     */
    private var _errorSnackbar = MutableLiveData<String?>(null)
    val errorSnackbar: LiveData<String?>
        get() = _errorSnackbar

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
     * Cria um cartão a partir dos dados informados pelo usuário e submete à validaçãp
     * por meio da classe BusinessCardValidation. Recebe de volta o objeto Either<T, U>
     * e faz a gravação do cartão (se válido) ou o tratamento da exceção (se não for válido).
     */
    fun createNewCard() {
        val mBusinessCard = BusinessCard(
            nome = nomeField.value.toString(),
            empresa = empresaField.value.toString(),
            telefone = telefoneField.value.toString(),
            email = emailField.value.toString(),
            cardColor = cardColorField.value.toString()
        )
        val either = BusinessCardValidation.validate(mBusinessCard) as Either.BusinessCardEither
        with(either) {
            businessCard?.let {
                _newCard.value = it
                saveCard()
            }
            exception?.let {
                _errorSnackbar.value = it.message
                errorSnackbarShown()
            }
        }
    }

    /**
     * Reseta o Snackbar depois de exibido
     */
    private fun errorSnackbarShown() {
        _errorSnackbar.value = null
    }

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
            launchDataSave {
                newCard.value?.let {
                    saveToDatabaseUseCase.updateCardInDatabase(
                        it.copy(id = receivedCard?.id!!)
                    )
                }
            }
        } else {
            launchDataSave {
                newCard.value?.let {
                    saveToDatabaseUseCase
                        .saveNewCardToDatabase(it)
                }
            }

        }
        doneNavigateToHomeFragment()
    }

    /**
     * Reseta o _newCard depois de fazer a gravação
     */
    private fun doneNavigateToHomeFragment() {
        _newCard.value = null
    }

    /**
     * Uma função do tipo suspend lambda para organizar e simplificar as chamadas de funcões
     * de suspensão.
     */
    private fun launchDataSave(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                block()
            } catch (ex: Exception) {
                _errorSnackbar.value = ex.message
            }
        }
    }

    /**
     * Recebe um BusinessCard e atribui à variável _receivedCard
     */
    fun receiveCard(businessCard: BusinessCard) {
        with(businessCard) {
            _receivedCard.value = businessCard
            initFields(this)
        }
    }

    /**
     * Inicializa os campos do formulário com os valores do
     * cartão recebido.
     */
    private fun initFields(businessCard: BusinessCard) {
        with(businessCard) {
            nomeField.value = nome
            telefoneField.value = telefone
            empresaField.value = empresa
            emailField.value = email
        }
    }

}