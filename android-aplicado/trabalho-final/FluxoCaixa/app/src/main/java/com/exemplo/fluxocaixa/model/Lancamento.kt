package com.exemplo.fluxocaixa.model

data class Lancamento(
    val id: Long = 0,
    val valor: Double,
    val descricao: String,
    val dataLancamento: String,
    val tipoLancamento: TipoLancamento
)
