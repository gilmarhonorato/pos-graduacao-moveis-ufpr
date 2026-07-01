package com.exemplo.fluxocaixa.util

import java.text.NumberFormat
import java.util.Locale

object FormatadorMoeda {
    private val formatoBrasileiro = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"))

    fun formatar(valor: Double): String = formatoBrasileiro.format(valor)

    fun extrairCentavos(texto: String): Long {
        val apenasDigitos = texto.filter { it.isDigit() }
        if (apenasDigitos.isEmpty()) return 0L
        return apenasDigitos.toLong()
    }

    fun converterTextoParaValor(texto: String): Double? {
        val centavos = extrairCentavos(texto)
        if (centavos == 0L) return null
        return centavos / 100.0
    }
}
