package br.com.chicorialabs.businesscard.data

import androidx.lifecycle.LiveData

/**
 * Um repositório com acesso à database por meio da interface BusinessCardDao.
 *
 *
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
     * Esse método retorna um único BusinessCard do repositório.
     */
    fun get(businessCard: BusinessCard) : BusinessCard? =
        businessCardDao.get(businessCard.id)

    /**
     * Os métodos de save(), updade() e delete() precisam ser invocados a partir de
     * funções de suspensão. No ViewModel eu faço o tratamento das corrotinas,
     * invocando as operações dentro do viewModelScope.
     */

    suspend fun save(businessCard: BusinessCard) {
        businessCardDao.insert(businessCard)
    }

    suspend fun update(businessCard: BusinessCard) {
        businessCardDao.update(businessCard)
    }

    suspend fun delete(businessCard: BusinessCard) {
        businessCardDao.delete(businessCard)
    }

}