package br.com.chicorialabs.businesscard.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.chicorialabs.businesscard.domain.BusinessCard
import br.com.chicorialabs.businesscard.databinding.ItemBusinesscardBinding

//TODO 001: Criar uma sealed class DataItem
//TODO 002: Criar um item XML para o Header
//TODO 003: Criar constantes para os ViewTypes
//TODO 004: Sobrescrever o método getItemViewType()
//TODO 005: Reescrever o método onCreateViewHolder()
//TODO 006: Reescrever a definição do Adapter com classes abstratas DataItem e DataItemViewHolder

/**
 * O adapter implementa a interface ListAdapter com DiffUtil; tanto a inflação do layout
 * quanto a vinculação de dados ocorrem na classe ViewHolder.
 */
class BusinessCardAdapter(val cardListener: BusinessCardListener) : ListAdapter<BusinessCard,
        BusinessCardAdapter.BusinessCardViewHolder>(BusinessCardDiffCallback()) {

//    TODO 011: Definir um escopo de corrotina para o adapter



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessCardViewHolder =
        BusinessCardViewHolder.from(parent)

    override fun onBindViewHolder(holder: BusinessCardViewHolder, position: Int) {
        holder.bind(getItem(position), cardListener)
    }

//    TODO 009: Criar um método addHeaderAndSubmitList()
//    TODO 012: Refatorar o método addHeaderAndSubmitList para usar o adapterScope



//    TODO 007: Criar uma sealed class DataItemViewHolder que estende RecyclerView.ViewHolder
    /**
     * Implementei o ViewHolder como uma classe aninhada para conseguir adotar a boa prática
     * de fazer a inflação do ViewHolder a partir de si mesmo. O binding dos dados e métodos
     * também é responsabilidade da classe ViewHolder. Necessita também um binding object
     * definido no XML do item.
     */
    class BusinessCardViewHolder(val binding: ItemBusinesscardBinding) :
        RecyclerView.ViewHolder(binding.root) {

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
     * Essa classe implementa a interface DiffUtil.ItemCallback<T>. O código aqui é
     * quase boilerplate.
     */
    class BusinessCardDiffCallback : DiffUtil.ItemCallback<BusinessCard>() {

        override fun areItemsTheSame(oldItem: BusinessCard, newItem: BusinessCard): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BusinessCard, newItem: BusinessCard): Boolean {
            return oldItem == newItem
        }

    }




}