package com.exemplo.myapplication.ui.extrato

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exemplo.myapplication.data.Movimentacao
import com.exemplo.myapplication.data.MovimentacaoRepository
import com.exemplo.myapplication.data.TipoMovimentacao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ExtratoViewModel(
    repositorio: MovimentacaoRepository
) : ViewModel() {

    val estado: StateFlow<ExtratoUiState> = repositorio.movimentacoes
        .map { movimentacoes ->
            ExtratoUiState(
                movimentacoes = movimentacoes,
                saldo = calcularSaldo(movimentacoes)
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ExtratoUiState()
        )

    companion object {
        fun calcularSaldo(movimentacoes: List<Movimentacao>): Double =
            movimentacoes.sumOf { movimentacao ->
                when (movimentacao.tipo) {
                    TipoMovimentacao.RECEITA -> movimentacao.valor
                    TipoMovimentacao.DESPESA -> -movimentacao.valor
                }
            }
    }
}

data class ExtratoUiState(
    val movimentacoes: List<Movimentacao> = emptyList(),
    val saldo: Double = 0.0
)
