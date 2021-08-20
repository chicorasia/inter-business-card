package br.com.chicorialabs.businesscard.domain

import br.com.chicorialabs.businesscard.exception.InvalidNameException
import org.junit.Assert.*
import org.junit.Test

class BusinessCardValidationTest {

    val mInvalidBusinessCard = BusinessCard(
        nome = "ab",
        empresa = "Test Company LLC",
        telefone = "10020004000",
        email = "teste@test.com",
        cardColor = "#FFA67F38"
    )

    val mValidBusinessCard = BusinessCard(
        nome = "Tyler Durden",
        empresa = "Test Company LLC",
        telefone = "10020004000",
        email = "teste@test.com",
        cardColor = "#FFA67F38"
    )

    @Test
    fun deve_RetornarUmEitherComBusinessCard_AoReceberDadosValidos() {

        val either = BusinessCardValidation.validate(mValidBusinessCard)

        assertTrue(either.t is BusinessCard)
        assertTrue(either.u == null)

    }

    @Test
    fun deve_RetornarUmEitherComException_AoReceberDadosInValidos() {

        val either = BusinessCardValidation.validate(mInvalidBusinessCard)

        assertTrue(either.t == null)
        assertTrue(either.u is InvalidNameException)
        assertTrue(either.u?.message == "O nome precisa ter ao menos 3 caracteres.")

    }


}

