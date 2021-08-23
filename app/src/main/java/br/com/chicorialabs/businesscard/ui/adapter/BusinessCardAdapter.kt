package br.com.chicorialabs.businesscard.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import br.com.chicorialabs.businesscard.databinding.ItemBusinesscardBinding
import br.com.chicorialabs.businesscard.databinding.ItemHeaderBinding
import br.com.chicorialabs.businesscard.domain.BusinessCard
import br.com.chicorialabs.businesscard.extension.toListOfDataItem

/**
 * O adapter implementa a interface ListAdapter com DiffUtil; tanto a inflação do layout
 * quanto a vinculação de dados ocorrem na classe ViewHolder.
 */
class BusinessCardAdapter(val cardListener: BusinessCardListener) : ListAdapter<DataItem,
        BusinessCardAdapter.DataItemViewHolder>(BusinessCardDiffCallback()) {

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
                holder.bind(item.key)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.BusinessCardItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    /**
     * Processa a lista recebida do repositório para agrupar os contatos
     * conforme a primeira inicial e submete.
     */
    fun addHeadersAndSubmitList(list: List<BusinessCard>?) {

        val listDataItem = list?.toListOfDataItem()
        submitList(listDataItem)
    }


    sealed class DataItemViewHolder(open val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Implementei o ViewHolder como uma classe aninhada para conseguir adotar a boa prática
         * de fazer a inflação do ViewHolder a partir de si mesmo. O binding dos dados e métodos
         * também é responsabilidade da classe ViewHolder. Necessita também um binding object
         * definido no XML do item.
         */
        class BusinessCardViewHolder(override val binding: ItemBusinesscardBinding) :
            DataItemViewHolder(binding) {

            /**
             * Esse método faz a vinculação dos dados com os componentes visuais. É possível fazer
             * tudo manualmente aqui na classe ViewHolder, mas preferi adotar BindingAdapters para
             * deixar a solução mais flexível e fácil de manter.
             */
            fun bind(item: BusinessCard, cardListener: BusinessCardListener) {
                with(binding) {
                    businessCard = item
                    listener = cardListener
                    executePendingBindings()
                }
            }

            /**
             * Esse companion object permite que o ListAdapter obtenha uma instância
             * do ViewHolder passando apenas o parent.
             */
            companion object {
                fun from(parent: ViewGroup): BusinessCardViewHolder {
                    val binding: ItemBusinesscardBinding = ItemBusinesscardBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                    return BusinessCardViewHolder(binding)
                }
            }
        }


        /**
         * Essa classe representa um ViewHolder para o header da lista
         */
        class HeaderViewHolder(override val binding: ItemHeaderBinding) :
            DataItemViewHolder(binding) {

            fun bind(key: Char) {
                with(binding) {
                    itemHeaderTv.text = key.toString()
                }
            }

            companion object {
                fun from(parent: ViewGroup): HeaderViewHolder {
                    val binding: ItemHeaderBinding = ItemHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                    return HeaderViewHolder(binding)
                }
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