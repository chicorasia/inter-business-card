package br.com.chicorialabs.businesscard.domain

import java.lang.Exception

/**
 * Essa classe é usada como apoio para a validação das entidades, retornando um objeto que
 * tem uma entidade quando sua construção é válida ou uma exceção quando não passa nos
 * critérios de validação.
 */
sealed class Either<T, U>(val t: T?, val u: U?) {

    class BusinessCardEither<T, U>(
        val businessCard: BusinessCard?,
        val exception: Exception?
    ) : Either<T, U>(businessCard as T, exception as U)


}
