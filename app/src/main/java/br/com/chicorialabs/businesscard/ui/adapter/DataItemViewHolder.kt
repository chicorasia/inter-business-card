package br.com.chicorialabs.businesscard.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import br.com.chicorialabs.businesscard.databinding.ItemBusinesscardBinding
import br.com.chicorialabs.businesscard.databinding.ItemHeaderBinding
import br.com.chicorialabs.businesscard.domain.BusinessCard

/**
 * Essa classe mantém os diferentes ViewHolders. Optei por usar uma classe selada para limitar
 * a extensão dessa classe.
 */
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

        fun bind(item: DataItem.Header) {
            with(binding) {
                header = item
                executePendingBindings()
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
