package com.exemplo.myapplication.ui.lancamento

enum class CampoInvalido {
    VALOR,
    DESCRICAO
}

sealed interface ResultadoLancamento {
    data object Salvo : ResultadoLancamento
    data object Excluido : ResultadoLancamento
    data class Invalido(val campos: List<CampoInvalido>) : ResultadoLancamento
}
