package br.com.chicorialabs.businesscard.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Essa data class representa a única entidade usada no app. A anotação @Entity indica para o Room
 * que ela será gravada no armazenamento local. As entidades ficam gravadas na
 * tabela "table_businesscard". O campo id serve como chave primária e é gerado automaticamente.
 */

@Parcelize
@Entity(tableName = "table_businesscard")
data class BusinessCard(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val nome: String,
    val empresa: String,
    val telefone: String,
    val email: String,
    val cardColor: String
    ) : Parcelable
