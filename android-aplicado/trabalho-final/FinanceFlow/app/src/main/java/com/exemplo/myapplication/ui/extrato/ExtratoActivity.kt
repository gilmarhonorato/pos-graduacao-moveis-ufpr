package com.exemplo.myapplication.ui.extrato

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import com.exemplo.myapplication.R
import com.exemplo.myapplication.data.Dependencias
import com.exemplo.myapplication.data.Movimentacao
import com.exemplo.myapplication.databinding.ActivityExtratoBinding
import com.exemplo.myapplication.ui.Formatadores
import com.exemplo.myapplication.ui.lancamento.LancamentoActivity
import kotlinx.coroutines.launch

class ExtratoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExtratoBinding
    private lateinit var adapter: MovimentacaoAdapter

    private val viewModel: ExtratoViewModel by viewModels {
        viewModelFactory {
            initializer { ExtratoViewModel(Dependencias.repositorio(applicationContext)) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityExtratoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        aplicarInsets()
        configurarLista()
        configurarAcoes()
        observarEstado()
    }

    private fun aplicarInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.telaExtrato) { view, insets ->
            val barras = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(barras.left, barras.top, barras.right, barras.bottom)
            insets
        }
    }

    private fun configurarLista() {
        adapter = MovimentacaoAdapter(::abrirEdicao)
        binding.listaMovimentacoes.layoutManager = LinearLayoutManager(this)
        binding.listaMovimentacoes.adapter = adapter
    }

    private fun configurarAcoes() {
        binding.fabNovoLancamento.setOnClickListener { abrirNovoLancamento() }
    }

    private fun abrirNovoLancamento() {
        startActivity(Intent(this, LancamentoActivity::class.java))
    }

    private fun abrirEdicao(movimentacao: Movimentacao) {
        startActivity(LancamentoActivity.intentEdicao(this, movimentacao))
    }

    private fun observarEstado() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.estado.collect { estado ->
                    adapter.submitList(estado.movimentacoes)
                    binding.textoVazio.visibility =
                        if (estado.movimentacoes.isEmpty()) View.VISIBLE else View.GONE
                    exibirSaldo(estado.saldo)
                }
            }
        }
    }

    private fun exibirSaldo(saldo: Double) {
        binding.textoSaldo.text = Formatadores.moeda(saldo)
        val cor = if (saldo < 0) R.color.vermelho_despesa else R.color.verde_receita
        binding.textoSaldo.setTextColor(ContextCompat.getColor(this, cor))
    }
}
