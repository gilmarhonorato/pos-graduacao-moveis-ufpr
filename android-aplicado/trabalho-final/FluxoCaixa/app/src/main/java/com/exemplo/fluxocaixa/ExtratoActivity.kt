package com.exemplo.fluxocaixa

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exemplo.fluxocaixa.adapter.LancamentoAdapter
import com.exemplo.fluxocaixa.database.LancamentoRepositorio
import com.exemplo.fluxocaixa.util.FormatadorMoeda
import com.exemplo.fluxocaixa.util.MargemSistema
import com.google.android.material.appbar.MaterialToolbar

class ExtratoActivity : AppCompatActivity() {

    private lateinit var repositorio: LancamentoRepositorio
    private lateinit var adaptador: LancamentoAdapter
    private lateinit var listaLancamentos: RecyclerView
    private lateinit var textoSaldoTotal: TextView
    private lateinit var textoListaVazia: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_extrato)
        MargemSistema.aplicar(findViewById(R.id.telaExtrato))

        val barraSuperior = findViewById<MaterialToolbar>(R.id.barraSuperior)
        setSupportActionBar(barraSuperior)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        repositorio = LancamentoRepositorio(this)
        vincularComponentes()
        configurarLista()
    }

    override fun onResume() {
        super.onResume()
        carregarExtrato()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun vincularComponentes() {
        listaLancamentos = findViewById(R.id.listaLancamentos)
        textoSaldoTotal = findViewById(R.id.textoSaldoTotal)
        textoListaVazia = findViewById(R.id.textoListaVazia)
    }

    private fun configurarLista() {
        adaptador = LancamentoAdapter()
        listaLancamentos.layoutManager = LinearLayoutManager(this)
        listaLancamentos.adapter = adaptador
    }

    private fun carregarExtrato() {
        val lancamentos = repositorio.listarLancamentos()
        adaptador.atualizarLista(lancamentos)

        val saldoTotal = repositorio.calcularSaldoTotal()
        textoSaldoTotal.text = FormatadorMoeda.formatar(saldoTotal)

        val corSaldo = if (saldoTotal >= 0) R.color.verde_receita else R.color.vermelho_despesa
        textoSaldoTotal.setTextColor(ContextCompat.getColor(this, corSaldo))

        val listaVazia = lancamentos.isEmpty()
        textoListaVazia.visibility = if (listaVazia) View.VISIBLE else View.GONE
        listaLancamentos.visibility = if (listaVazia) View.GONE else View.VISIBLE
    }
}
