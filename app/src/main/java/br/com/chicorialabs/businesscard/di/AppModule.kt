package br.com.chicorialabs.businesscard.di

import br.com.chicorialabs.businesscard.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Esse arquivo mantém as listas de módulos do Koin, agrupados conforme
 * suas finalidades. Por enquanto tem apenas os módulos de ViewModel.
 *
 */

val viewModelModule = module {
    viewModel { HomeViewModel() }
}