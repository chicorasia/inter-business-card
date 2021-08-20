package br.com.chicorialabs.businesscard.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import br.com.chicorialabs.businesscard.domain.BusinessCard


/**
 * Esse use case filtra a lista conforme a string searchQuery, usando a função
 * Transformations.switchMap { }. Ele observa mudanças no valor da searchQuery
 * e atualiza a lista, emitindo-a como uma LiveData<List<BusinessCard>>. O filtro
 * busca por matches em 3 campos: nome, email e empresa.
 */
class ApplySearchFilterUseCase {

    fun filterList(
        list: LiveData<List<BusinessCard>>,
        searchQuery: LiveData<CharSequence>
    ): LiveData<List<BusinessCard>> =
        Transformations.switchMap(searchQuery) {
            list.map { list ->
                searchQuery.value?.let {
                    list.filter { businessCard ->
                        val query = searchQuery.value.toString()
                        with(businessCard) {
                            applyQuery(query)
                        }
                    }
                }
            }
        }

    private fun BusinessCard.applyQuery(query: String) =
        empresa.contains(query, true) ||
                nome.contains(query, true) ||
                email.contains(query, true)
}