package com.exemplo.myapplication.ui.extrato

import com.exemplo.myapplication.data.Movimentacao
import com.exemplo.myapplication.data.TipoMovimentacao
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculoSaldoTest {

    private fun receita(valor: Double) =
        Movimentacao(valor = valor, descricao = "receita", data = 0L, tipo = TipoMovimentacao.RECEITA)

    private fun despesa(valor: Double) =
        Movimentacao(valor = valor, descricao = "despesa", data = 0L, tipo = TipoMovimentacao.DESPESA)

    @Test
    fun listaVaziaResultaEmSaldoZero() {
        assertEquals(0.0, ExtratoViewModel.calcularSaldo(emptyList()), 0.001)
    }

    @Test
    fun somaReceitasESubtraiDespesas() {
        val movimentacoes = listOf(
            receita(1000.0),
            despesa(250.0),
            receita(50.5),
            despesa(100.5)
        )

        assertEquals(700.0, ExtratoViewModel.calcularSaldo(movimentacoes), 0.001)
    }

    @Test
    fun saldoPodeSerNegativo() {
        val movimentacoes = listOf(receita(100.0), despesa(150.0))

        assertEquals(-50.0, ExtratoViewModel.calcularSaldo(movimentacoes), 0.001)
    }
}
