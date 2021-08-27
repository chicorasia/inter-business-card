package br.com.chicorialabs.businesscard.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import br.com.chicorialabs.businesscard.domain.BusinessCard
import br.com.chicorialabs.businesscard.databinding.ItemBusinesscardBinding
import br.com.chicorialabs.businesscard.databinding.ItemHeaderBinding
import br.com.chicorialabs.businesscard.extension.toListOfDataItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException

//TODO 001: Criar uma sealed class DataItem *
//TODO 002: Criar um item XML para o Header *
//TODO 003: Criar uma sealed class DataItemViewHolder que estende RecyclerView.ViewHolder *
//TODO 004: Reescrever a definição do Adapter com classes abstratas DataItem e DataItemViewHolder  *
//TODO 005: Criar um DataItemDiffCallback() *
//TODO 006: Criar constantes para os ViewTypes *
//TODO 007: Sobrescrever o método getItemViewType() *
//TODO 008: Reescrever o método onCreateViewHolder() *
//TODO 009: Reescrever o método onBindViewHolder() *

/**
 * O adapter implementa a interface ListAdapter com DiffUtil; tanto a inflação do layout
 * quanto a vinculação de dados ocorrem na classe ViewHolder.
 */
class BusinessCardAdapter(val cardListener: BusinessCardListener) : ListAdapter<DataItem,
        BusinessCardAdapter.DataItemViewHolder>(DataItemDiffCallback()) {

    private val ITEM_VIEW_TYPE_HEADER = 0
    private val ITEM_VIEW_TYPE_BUSINESSCARDITEM = 1


//    TODO 013: Definir um escopo de corrotina para o adapter
    val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.BusinessCardItem -> ITEM_VIEW_TYPE_BUSINESSCARDITEM
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) :
            DataItemViewHolder {
        return when(viewType) {
            ITEM_VIEW_TYPE_HEADER -> DataItemViewHolder.HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_BUSINESSCARDITEM -> DataItemViewHolder.BusinessCardViewHolder.from(parent)
            else -> throw ClassCastException("ViewType não reconhecido: $viewType")
        }
    }

    override fun onBindViewHolder(holder: DataItemViewHolder, position: Int) {
        return when(holder) {
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

//    TODO 011: Criar um método addHeaderAndSubmitList()
//    TODO 014: Refatorar o método addHeaderAndSubmitList para usar o adapterScope

    fun addHeadersAndSubmitList(list: List<BusinessCard>?) {

        adapterScope.launch {
            val listDataItem = list?.toListOfDataItem()
            withContext(Dispatchers.Main) {
                submitList(listDataItem)
            }
        }

    }


    /**
     * Implementei o ViewHolder como uma classe aninhada para conseguir adotar a boa prática
     * de fazer a inflação do ViewHolder a partir de si mesmo. O binding dos dados e métodos
     * também é responsabilidade da classe ViewHolder. Necessita também um binding object
     * definido no XML do item.
     */


    /**
     * Essa classe implementa a interface DiffUtil.ItemCallback<T>. O código aqui é
     * quase boilerplate.
     */


    class DataItemDiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }

    }

    sealed class DataItemViewHolder(open val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root){

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
                fun from(parent: ViewGroup): DataItemViewHolder {
                    val binding: ItemBusinesscardBinding = ItemBusinesscardBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                    return BusinessCardViewHolder(binding)
                }
            }
        }

        class HeaderViewHolder(override val binding: ItemHeaderBinding) :
            DataItemViewHolder(binding) {

            /**
             * Esse método faz a vinculação dos dados com os componentes visuais. É possível fazer
             * tudo manualmente aqui na classe ViewHolder, mas preferi adotar BindingAdapters para
             * deixar a solução mais flexível e fácil de manter.
             */
            fun bind(item: DataItem.Header) {
                with(binding) {
                    itemHeaderTv.text = item.key.toString()
                }
            }

            /**
             * Esse companion object permite que o ListAdapter obtenha uma instância
             * do ViewHolder passando apenas o parent.
             */
            companion object {
                fun from(parent: ViewGroup): DataItemViewHolder {
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

}