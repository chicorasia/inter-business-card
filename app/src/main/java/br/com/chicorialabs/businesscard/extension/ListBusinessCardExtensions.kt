package br.com.chicorialabs.businesscard.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import br.com.chicorialabs.businesscard.domain.BusinessCard

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

//TODO 008: Criar uma extension function para agrupar os contatos por inicial