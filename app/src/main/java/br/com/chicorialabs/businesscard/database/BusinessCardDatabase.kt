package br.com.chicorialabs.businesscard.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.chicorialabs.businesscard.domain.BusinessCard

/**
 * Essa classe abstrata representa a database. Ela mantém somente a entidade BusinessCard.
 * Usei o código boilerplate sugerido no codelab da Google.
 */

@Database(entities = [BusinessCard::class], version = 1, exportSchema = false)
abstract class BusinessCardDatabase : RoomDatabase() {

    /**
     * Esse campo é acessado pelo Koin (ver o arquivo AppModule.kt)
     */
    abstract val businessCardDao: BusinessCardDao

    /**
     * A função do companion object é verificar se já existe uma instância válida da Database
     * e, caso não exista, criar um nova instância
     */
    companion object {

        @Volatile
        private var INSTANCE: BusinessCardDatabase? = null

        fun getInstance(context: Context) : BusinessCardDatabase {
            synchronized(this) {

                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BusinessCardDatabase::class.java,
                        "businesscard_db"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}