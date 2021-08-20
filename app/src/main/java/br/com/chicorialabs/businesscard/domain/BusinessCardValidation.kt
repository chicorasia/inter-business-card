package br.com.chicorialabs.businesscard.domain

import br.com.chicorialabs.businesscard.exception.InvalidNameException

/**
 * Essa classe mantém as regras de negócio necessárias para a validação das entradas no formulário:
 * 1: o nome precisa ter ao menos 3 caracteres
 */
class BusinessCardValidation {

    companion object {

        const val NAME_TOO_SHORT_EXCEPTION = "O nome precisa ter ao menos 3 caracteres."

        fun validate(businessCard: BusinessCard): Either<BusinessCard?, Exception?> {
            return when (businessCard.nome.length) {
                0, 1, 2 -> Either.BusinessCardEither<BusinessCard?, Exception?>(
                    null,
                    InvalidNameException(NAME_TOO_SHORT_EXCEPTION)
                )
                else -> Either.BusinessCardEither<BusinessCard?, Exception?>(businessCard, null)
            }
        }
    }

}

