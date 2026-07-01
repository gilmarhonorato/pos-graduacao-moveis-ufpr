package com.exemplo.myapplication.ui

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object Formatadores {

    private val localeBrasil = Locale.forLanguageTag("pt-BR")
    private val formatoMoeda = NumberFormat.getCurrencyInstance(localeBrasil)
    private val formatoData = SimpleDateFormat("dd/MM/yyyy", localeBrasil).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    fun moeda(valor: Double): String = formatoMoeda.format(valor)

    fun data(epochMillis: Long): String = formatoData.format(Date(epochMillis))
}
