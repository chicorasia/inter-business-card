package br.com.chicorialabs.businesscard

import android.app.Application
import br.com.chicorialabs.businesscard.di.daoModule
import br.com.chicorialabs.businesscard.di.repositoryModule
import br.com.chicorialabs.businesscard.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * Essa classe é o ponto de acesso ao aplicativo. No método onCreate() se faz a inicialização
 * da biblioteca Koin, passando as listas de módulos. Nesse caso são três listas diferentes:
 * viewModelModule, daoModule e repositoryModule.
 */
class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(viewModelModule)
            modules(repositoryModule)
            modules(daoModule)
        }
    }
}