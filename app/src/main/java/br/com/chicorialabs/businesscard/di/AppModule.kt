package br.com.chicorialabs.businesscard.di

import br.com.chicorialabs.businesscard.database.BusinessCardDatabase
import br.com.chicorialabs.businesscard.database.BusinessCardRepository
import br.com.chicorialabs.businesscard.ui.addcard.AddCardViewModel
import br.com.chicorialabs.businesscard.ui.home.HomeViewModel
import br.com.chicorialabs.businesscard.usecase.ApplySearchFilterUseCase
import br.com.chicorialabs.businesscard.usecase.ReadFromDatabaseUseCase
import br.com.chicorialabs.businesscard.usecase.RemoveFromDatabaseUseCase
import br.com.chicorialabs.businesscard.usecase.SaveToDatabaseUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Esse arquivo mantém as listas de módulos do Koin, agrupados conforme
 * suas finalidades. Uso o delegate single para repositoryModule e
 * daoModule para que esses objetos sejam instanciados como singletons.
 *
 * Com o método get() passado como parâmetro do BusinessCardRepository o Koin
 * sabe que precisa passar uma instância do dao para o construtor da classe.
 * O mesmo vale para o HomeViewModel - que, no caso, recebe uma instância
 * do BusinessCardRepository.
 *
 */

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { AddCardViewModel(get()) }
}

val repositoryModule = module {
    single { BusinessCardRepository(get()) }
}

val daoModule = module {
    single { BusinessCardDatabase.getInstance(androidContext()).businessCardDao }
}

val useCaseModule = module {
    factory { SaveToDatabaseUseCase(get()) }
    factory { ApplySearchFilterUseCase() }
    factory { RemoveFromDatabaseUseCase(get()) }
    factory { ReadFromDatabaseUseCase(get()) }
}