package com.exemplo.myapplication.data

import kotlinx.coroutines.flow.Flow

class MovimentacaoRepository(private val dao: MovimentacaoDao) {

    val movimentacoes: Flow<List<Movimentacao>> = dao.listarTodas()

    suspend fun salvar(movimentacao: Movimentacao): Long {
        return if (movimentacao.id == 0L) {
            dao.inserir(movimentacao)
        } else {
            dao.atualizar(movimentacao)
            movimentacao.id
        }
    }

    suspend fun excluir(id: Long) = dao.excluir(id)

    suspend fun buscarPorId(id: Long): Movimentacao? = dao.buscarPorId(id)
}
