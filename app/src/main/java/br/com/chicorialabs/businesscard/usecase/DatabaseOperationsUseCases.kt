package br.com.chicorialabs.businesscard.usecase

import androidx.lifecycle.LiveData
import br.com.chicorialabs.businesscard.database.BusinessCardRepository
import br.com.chicorialabs.businesscard.domain.BusinessCard

/**
 * Esse use case é responsável pelas operações de leitura dos dados do repositório
 */
class ReadFromDatabaseUseCase (repository: BusinessCardRepository) {

    val listBusinessCard: LiveData<List<BusinessCard>> =
        repository.listBusinessCard

}

/**
 * Esse use case é responsável pelas operações de remoção de dados do repositório
 */
class RemoveFromDatabaseUseCase (private val repository: BusinessCardRepository) {

    suspend fun remove(businessCard: BusinessCard) {
        repository.remove(businessCard)
    }

}

/**
 * Esse UseCase é responsável pelas operações de gravação no repositorio
 */
class SaveToDatabaseUseCase(private val repository: BusinessCardRepository) {

    suspend fun saveNewCardToDatabase(
        businessCard: BusinessCard,
    ) {
        repository.save(businessCard)
    }

    suspend fun updateCardInDatabase(
        businessCard: BusinessCard,
    ) {
        repository.update(businessCard)
    }

}