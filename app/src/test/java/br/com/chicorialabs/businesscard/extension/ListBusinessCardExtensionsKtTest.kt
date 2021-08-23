package br.com.chicorialabs.businesscard.extension

import android.provider.ContactsContract
import br.com.chicorialabs.businesscard.domain.BusinessCard
import br.com.chicorialabs.businesscard.ui.adapter.DataItem
import org.junit.Assert.*
import org.junit.Test

class ListBusinessCardExtensionsKtTest {

    val listOfBusinessCard = listOf<BusinessCard>(
        BusinessCard(nome = "Albus", email = "albus@hogwarts.edu", empresa = "Hogwarts", telefone = "600", cardColor = "#A45BCC"),
        BusinessCard(nome = "Hagrid", email = "hagrid@hogwarts.edu", empresa = "Hogwarts", telefone = "600", cardColor = "#A45BCC")
    )

    @Test
    fun deve_AdicionarHeaders_AoReceberUmaListaDeBusinessCard() {

        val listDataItem: List<DataItem>? = listOfBusinessCard.toListOfDataItem()
        println(listDataItem)
        assert(listDataItem?.size == 4)

    }


}