package com.exemplo.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MovimentacaoDao {

    @Insert
    suspend fun inserir(movimentacao: Movimentacao): Long

    @Update
    suspend fun atualizar(movimentacao: Movimentacao)

    @Query("DELETE FROM movimentacoes WHERE id = :id")
    suspend fun excluir(id: Long)

    @Query("SELECT * FROM movimentacoes WHERE id = :id")
    suspend fun buscarPorId(id: Long): Movimentacao?

    @Query("SELECT * FROM movimentacoes ORDER BY data DESC, id DESC")
    fun listarTodas(): Flow<List<Movimentacao>>
}
