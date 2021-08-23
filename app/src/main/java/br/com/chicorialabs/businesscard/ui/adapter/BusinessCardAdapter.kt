package br.com.chicorialabs.businesscard.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import br.com.chicorialabs.businesscard.domain.BusinessCard
import br.com.chicorialabs.businesscard.extension.toListOfDataItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * O adapter implementa a interface ListAdapter com DiffUtil; tanto a inflação do layout
 * quanto a vinculação de dados ocorrem na classe ViewHolder.
 * Ao invés de receber uma lista de BusinessCard, esse adapter espera uma List<DataItem>,
 * que é a superclasse dos tipos concretos de dados (BusinessCard ou Header).
 */
class BusinessCardAdapter(val cardListener: BusinessCardListener) : ListAdapter<DataItem,
        DataItemViewHolder>(BusinessCardDiffCallback()) {

    /**
     * Declarar um escopo de corrotina específico para o Adapter. Vai ser usado para as
     * situações em que é necessário processar a lista.
     */
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    /**
     * Essas constantes indicam o tipo de item que vai ser retornado.
     */
    private val ITEM_VIEW_TYPE_HEADER = 0
    private val ITEM_VIEW_TYPE_ITEM = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataItemViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> DataItemViewHolder.HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> DataItemViewHolder.BusinessCardViewHolder.from(parent)
            else -> throw ClassCastException("ViewType desconhecido ${viewType}")
        }
    }

    /**
     * Seleciona o binding dos dados conforme o tipo de ViewHolder.
     */
    override fun onBindViewHolder(holder: DataItemViewHolder, position: Int) {
        when (holder) {
            is DataItemViewHolder.BusinessCardViewHolder -> {
                val item = getItem(position) as DataItem.BusinessCardItem
                holder.bind(item.businessCard, cardListener)
            }
            is DataItemViewHolder.HeaderViewHolder -> {
                val item = getItem(position) as DataItem.Header
                holder.bind(item)
            }
        }

    }

    /**
     * Determinar o tipo de ViewHolder a partir do objeto na posição da lista.
     */
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.BusinessCardItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    /**
     * Processa a lista recebida do repositório para agrupar os contatos
     * conforme a primeira inicial e submete. O processamento acontece
     * no escopo específico do Adapter para não bloquear a Thread principal.
     */
    fun addHeadersAndSubmitList(list: List<BusinessCard>?) {

        adapterScope.launch {
            val listDataItem = list?.toListOfDataItem()
            withContext(Dispatchers.Main) {
                submitList(listDataItem)
            }
        }

    }

    /**
     * Essa classe implementa a interface DiffUtil.ItemCallback<T>. O código aqui é
     * quase boilerplate.
     */
    class BusinessCardDiffCallback : DiffUtil.ItemCallback<DataItem>() {

        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }

    }



}