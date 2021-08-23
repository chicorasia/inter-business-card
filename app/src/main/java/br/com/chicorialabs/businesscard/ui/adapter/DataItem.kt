package br.com.chicorialabs.businesscard.ui.adapter

import br.com.chicorialabs.businesscard.domain.BusinessCard

/**
 * Essa classe selada é uma abstração dos tipos de itens da RecyclerView.
 * BusinessCardItem: é um cartão de visitas com os dados do contato.
 * Header: um divisor de lista que tem apenas um caractere, usado para separar
 * os contatos conforme a primeira inicial.
 */
sealed class DataItem {

    abstract val id: Long

    data class BusinessCardItem(val businessCard: BusinessCard,
                                override val id: Long = businessCard.id) : DataItem()

    data class Header(val key: Char,
                      override val id: Long = Long.MIN_VALUE): DataItem()

}