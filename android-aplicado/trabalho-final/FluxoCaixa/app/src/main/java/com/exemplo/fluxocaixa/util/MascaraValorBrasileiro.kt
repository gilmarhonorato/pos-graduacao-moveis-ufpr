package com.exemplo.fluxocaixa.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class MascaraValorBrasileiro(private val campo: EditText) : TextWatcher {

    private var atualizando = false

    override fun beforeTextChanged(texto: CharSequence?, inicio: Int, quantidade: Int, depois: Int) = Unit

    override fun onTextChanged(texto: CharSequence?, inicio: Int, antes: Int, quantidade: Int) = Unit

    override fun afterTextChanged(editable: Editable?) {
        if (atualizando) return

        atualizando = true
        val valorEmCentavos = FormatadorMoeda.extrairCentavos(editable?.toString().orEmpty())
        val textoFormatado = if (valorEmCentavos == 0L) {
            ""
        } else {
            FormatadorMoeda.formatar(valorEmCentavos / 100.0)
        }
        campo.setText(textoFormatado)
        campo.setSelection(textoFormatado.length)
        atualizando = false
    }

    companion object {
        fun aplicar(campo: EditText) {
            campo.addTextChangedListener(MascaraValorBrasileiro(campo))
        }
    }
}
