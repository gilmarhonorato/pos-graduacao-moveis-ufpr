package com.exemplo.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movimentacoes")
data class Movimentacao(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val valor: Double,
    val descricao: String,
    val data: Long,
    val tipo: TipoMovimentacao
)
