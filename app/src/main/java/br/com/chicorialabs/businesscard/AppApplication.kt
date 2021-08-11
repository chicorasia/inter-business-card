package br.com.chicorialabs.businesscard

import android.app.Application
import br.com.chicorialabs.businesscard.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * Essa classe é o ponto de acesso ao aplicativo. No método onCreate() se faz a inicialização
 * da biblioteca Koin, passando as listas de módulos.
 */
class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(viewModelModule)
        }
    }
}