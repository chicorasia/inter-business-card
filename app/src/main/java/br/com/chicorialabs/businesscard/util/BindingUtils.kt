package br.com.chicorialabs.businesscard.util

import android.graphics.Color
import android.widget.EditText
import android.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import br.com.chicorialabs.businesscard.R
import br.com.chicorialabs.businesscard.data.BusinessCard
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView


/**
 * Esse arquivo armazena os BindingAdapters para os itens da RecyclerView. Cada binding adapter
 * é definido como uma função de extensão da View para simplificar a sintaxe. Usei referências
 * nuláveis e safe-calls para maior segurança e robustez.
 */

@BindingAdapter("itemNomeTv")
fun MaterialTextView.setNome(item: BusinessCard?) {
    item?.let{ businessCard ->
        text = businessCard.nome
    }
}

@BindingAdapter("itemTelefoneTv")
fun MaterialTextView.setTelefone(item: BusinessCard?) {
    item?.let{ businessCard ->
        text = businessCard.telefone
    }
}

@BindingAdapter("itemEmailTv")
fun MaterialTextView.setEmail(item: BusinessCard?) {
    item?.let{ businessCard ->
        text = businessCard.email
    }
}

@BindingAdapter("itemEmpresaTv")
fun MaterialTextView.setEmpresa(item: BusinessCard?) {
    item?.let{ businessCard ->
        text = businessCard.empresa
    }
}

@BindingAdapter("itemBackgroundColor")
fun MaterialCardView.setItemBackGroundColor(item: BusinessCard?) {
    item?.let {
        setCardBackgroundColor(Color.parseColor(item.cardColor))
    }
}

@BindingAdapter("cardFormFragmentLabel")
fun MaterialTextView.setLabel(isEdit: Boolean) {
    isEdit?.let {
        if (!it) {
            text = context.getString(R.string.description_add_card_fragment)
        } else {
            text = context.getString(R.string.description_edit_card_fragment)
        }
    }
}

