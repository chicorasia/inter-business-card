package br.com.chicorialabs.businesscard.database

import androidx.lifecycle.LiveData
import br.com.chicorialabs.businesscard.domain.BusinessCard

/**
 * Um repositório com acesso à database por meio da interface BusinessCardDao.
 */
class BusinessCardRepository(
    private val businessCardDao: BusinessCardDao
) {

    /**
     * Encapsulando o acesso ao dao numa propriedade. Como o método getAll() não
     * é uma função de suspensão não preciso tomar nenhum cuidado especial com relação
     * às corrotinas.
     */
    val listBusinessCard: LiveData<List<BusinessCard>>
        get() = businessCardDao.getAll()

    /**
     * O método save() precisa ser invocado a partir de
     * funções de suspensão. No ViewModel eu faço o tratamento das corrotinas,
     * invocando as operações dentro do viewModelScope.
     */
    suspend fun save(businessCard: BusinessCard) {
        businessCardDao.insert(businessCard)
    }

    /**
     * Esse método apaga um BusinessCard do repositório. Também precisa ser uma
     * função de suspensão.
     */
    suspend fun remove(businessCard: BusinessCard) {
        businessCardDao.remove(businessCard)
    }

    fun get(cardId: Long): BusinessCard? = businessCardDao.get(cardId)

    suspend fun update(businessCard: BusinessCard) {
        businessCardDao.update(businessCard)
    }


}