package com.exemplo.fluxocaixa

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.exemplo.fluxocaixa.database.LancamentoRepositorio
import com.exemplo.fluxocaixa.model.Lancamento
import com.exemplo.fluxocaixa.model.TipoLancamento
import com.exemplo.fluxocaixa.util.FormatadorMoeda
import com.exemplo.fluxocaixa.util.MargemSistema
import com.exemplo.fluxocaixa.util.MascaraValorBrasileiro
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var repositorio: LancamentoRepositorio
    private lateinit var campoValor: TextInputEditText
    private lateinit var campoDescricao: TextInputEditText
    private lateinit var campoData: TextInputEditText
    private lateinit var radioReceita: RadioButton
    private lateinit var radioDespesa: RadioButton

    private val formatoData = SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("pt-BR"))
    private val calendarioSelecionado = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        MargemSistema.aplicar(findViewById(R.id.main))

        repositorio = LancamentoRepositorio(this)
        vincularComponentes()
        configurarDataInicial()
        configurarEventos()
    }

    private fun vincularComponentes() {
        campoValor = findViewById(R.id.campoValor)
        campoDescricao = findViewById(R.id.campoDescricao)
        campoData = findViewById(R.id.campoData)
        radioReceita = findViewById(R.id.radioReceita)
        radioDespesa = findViewById(R.id.radioDespesa)
    }

    private fun configurarDataInicial() {
        campoData.setText(formatoData.format(calendarioSelecionado.time))
    }

    private fun configurarEventos() {
        MascaraValorBrasileiro.aplicar(campoValor)
        campoData.setOnClickListener { abrirSeletorData() }

        findViewById<MaterialButton>(R.id.botaoSalvar).setOnClickListener {
            salvarLancamento()
        }

        findViewById<MaterialButton>(R.id.botaoVerLancamentos).setOnClickListener {
            startActivity(Intent(this, ExtratoActivity::class.java))
        }
    }

    private fun abrirSeletorData() {
        DatePickerDialog(
            this,
            { _, ano, mes, dia ->
                calendarioSelecionado.set(ano, mes, dia)
                campoData.setText(formatoData.format(calendarioSelecionado.time))
            },
            calendarioSelecionado.get(Calendar.YEAR),
            calendarioSelecionado.get(Calendar.MONTH),
            calendarioSelecionado.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun salvarLancamento() {
        val textoValor = campoValor.text?.toString()?.trim().orEmpty()
        val textoDescricao = campoDescricao.text?.toString()?.trim().orEmpty()
        val textoData = campoData.text?.toString()?.trim().orEmpty()

        val valorNumerico = FormatadorMoeda.converterTextoParaValor(textoValor)
        if (valorNumerico == null || valorNumerico <= 0) {
            Toast.makeText(this, R.string.erro_valor_invalido, Toast.LENGTH_SHORT).show()
            return
        }

        if (textoDescricao.isEmpty()) {
            Toast.makeText(this, R.string.erro_descricao_vazia, Toast.LENGTH_SHORT).show()
            return
        }

        if (textoData.isEmpty()) {
            Toast.makeText(this, R.string.erro_data_vazia, Toast.LENGTH_SHORT).show()
            return
        }

        val tipoLancamento = if (radioReceita.isChecked) TipoLancamento.RECEITA else TipoLancamento.DESPESA
        val lancamento = Lancamento(
            valor = valorNumerico,
            descricao = textoDescricao,
            dataLancamento = textoData,
            tipoLancamento = tipoLancamento
        )

        val idGerado = repositorio.salvarLancamento(lancamento)
        if (idGerado > 0) {
            Toast.makeText(this, R.string.mensagem_salvo, Toast.LENGTH_SHORT).show()
            limparFormulario()
        } else {
            Toast.makeText(this, R.string.erro_salvar, Toast.LENGTH_SHORT).show()
        }
    }

    private fun limparFormulario() {
        campoValor.text = null
        campoDescricao.text = null
        radioReceita.isChecked = true
        radioDespesa.isChecked = false
        calendarioSelecionado.timeInMillis = System.currentTimeMillis()
        campoData.setText(formatoData.format(calendarioSelecionado.time))
    }
}
