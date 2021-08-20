package br.com.chicorialabs.businesscard.usecase

import br.com.chicorialabs.businesscard.data.BusinessCardRepository
import br.com.chicorialabs.businesscard.domain.BusinessCard

class SaveToDatabaseUseCase {

    suspend fun saveNewCardToDatabase(
        businessCard: BusinessCard,
        repository: BusinessCardRepository
    ) {
        repository.save(businessCard)
    }

    suspend fun updateCardInDatabase(
        businessCard: BusinessCard,
        repository: BusinessCardRepository
    ) {
        repository.update(businessCard)
    }

}