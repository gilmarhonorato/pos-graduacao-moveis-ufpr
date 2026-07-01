package com.exemplo.fluxocaixa.model

import android.content.Context
import androidx.annotation.StringRes
import com.exemplo.fluxocaixa.R

enum class TipoLancamento(@StringRes private val rotuloResId: Int) {
    RECEITA(R.string.tipo_receita),
    DESPESA(R.string.tipo_despesa);

    fun ehReceita(): Boolean = this == RECEITA

    fun obterRotulo(contexto: Context): String = contexto.getString(rotuloResId)
}
