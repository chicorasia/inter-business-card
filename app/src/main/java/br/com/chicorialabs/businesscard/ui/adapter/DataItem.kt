package br.com.chicorialabs.businesscard.ui.adapter

import br.com.chicorialabs.businesscard.domain.BusinessCard

/**
 * Essa classe abstrai os itens da lista
 */
sealed class DataItem {

    abstract val id: Long

    data class BusinessCardItem(val businessCard: BusinessCard,
                                override val id: Long = businessCard.id) : DataItem()

    data class Header(val key: Char,
                      override val id: Long = Long.MIN_VALUE): DataItem()

}