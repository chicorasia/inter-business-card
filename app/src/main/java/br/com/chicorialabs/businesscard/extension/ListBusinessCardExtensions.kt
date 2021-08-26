package br.com.chicorialabs.businesscard.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import br.com.chicorialabs.businesscard.domain.BusinessCard
import br.com.chicorialabs.businesscard.ui.adapter.DataItem

/**
 * Uma função de extensão para ordenar a lista alfabeticamente pelo
 * nome do contato.
 */
fun LiveData<List<BusinessCard>>.sortedByName(): LiveData<List<BusinessCard>> =
    this.map { list ->
        list.sortedBy { businessCard ->
            businessCard.nome
        }
    }

//TODO 010: Criar uma extension function para agrupar os contatos por inicial
fun List<BusinessCard>.toListOfDataItem(): List<DataItem> {

    val grouping = this.groupBy { businessCard ->
        businessCard.nome.first()
    }

    val listDataItem = mutableListOf<DataItem>()
    grouping.forEach { mapEntry ->
        listDataItem.add(DataItem.Header(mapEntry.key))
        listDataItem.addAll(
            mapEntry.value.map { businessCard ->
                DataItem.BusinessCardItem(businessCard)
            }
        )
    }

    return listDataItem
}