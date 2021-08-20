package br.com.chicorialabs.businesscard.domain

import java.lang.Exception

class BusinessCardEither<T, U>(
    val businessCard: BusinessCard?,
    val exception: Exception?
) : Either<T, U>(businessCard as T, exception as U)