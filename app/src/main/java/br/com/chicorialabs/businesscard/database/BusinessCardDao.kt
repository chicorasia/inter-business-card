package br.com.chicorialabs.businesscard.database

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.chicorialabs.businesscard.domain.BusinessCard

/**
 * Uma interface para acesso aos dados por meio do repositório. Usando as annotations
 * do Room Database e as queries específicas. Usando o LiveData<T> o Room consegue manter
 * os dados atualizados então só é preciso buscar os dados uma vez.
 * Já deixei métodos declarados para buscar um único BusinessCard e para atualizar um BusinessCard.
 * Os métodos anotados com @Query aceitam qualquer chamada SQL.
 * O método getAll() não precisa (nem pode) ser uma função de suspensão por causa da
 * compatibilidade como LiveData<T>
 */
@Dao
interface BusinessCardDao {

    @Insert
    suspend fun insert(businessCard: BusinessCard)

    @Query("SELECT * FROM table_businesscard")
    fun getAll() : LiveData<List<BusinessCard>>

    @Delete
    suspend fun remove(businessCard: BusinessCard)

    @Query("SELECT * FROM table_businesscard WHERE id = :key")
    fun get(key: Long) : BusinessCard?

    @Update
    suspend fun update(businessCard: BusinessCard)

}