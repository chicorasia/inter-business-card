package br.com.chicorialabs.businesscard.ui.adapter

import android.view.View
import br.com.chicorialabs.businesscard.domain.BusinessCard

/**
 * Essa classe mantém os click listeners para o item da
 * lista de BusinessCard. Os comportamentos são implementados
 * no Fragment ou, de preferência, no ViewModel.
 */
class BusinessCardListener(
    val clickListener: (businessCard: BusinessCard) -> Unit,
    val shareBtnClickListener: (view: View) -> Unit,
    val longClickListener: (businessCard: BusinessCard) -> Boolean
) {

    fun onClick(businessCard: BusinessCard) {
        clickListener(businessCard)
    }

    fun onLongClick(businessCard: BusinessCard) : Boolean {
        longClickListener(businessCard)
        return true
    }

    fun onShareBtnClick(view: View) {
        shareBtnClickListener(view)
    }

}