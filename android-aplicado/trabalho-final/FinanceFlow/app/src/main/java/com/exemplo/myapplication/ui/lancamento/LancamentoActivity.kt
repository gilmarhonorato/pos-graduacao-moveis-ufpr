package com.exemplo.myapplication.ui.lancamento

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.exemplo.myapplication.R
import com.exemplo.myapplication.data.Dependencias
import com.exemplo.myapplication.data.Movimentacao
import com.exemplo.myapplication.data.TipoMovimentacao
import com.exemplo.myapplication.databinding.ActivityLancamentoBinding
import com.exemplo.myapplication.ui.Formatadores
import com.exemplo.myapplication.ui.extrato.ExtratoActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class LancamentoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLancamentoBinding

    private val viewModel: LancamentoViewModel by viewModels {
        viewModelFactory {
            initializer { LancamentoViewModel(Dependencias.repositorio(applicationContext)) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLancamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        aplicarInsets()
        configurarTipo()
        configurarData()
        configurarAcoes()
        observarEstado()
        carregarExtras()
    }

    private fun aplicarInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.telaLancamento) { view, insets ->
            val barras = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(barras.left, barras.top, barras.right, barras.bottom)
            insets
        }
    }

    private fun carregarExtras() {
        val id = intent.getLongExtra(EXTRA_ID, 0L)
        if (id == 0L) return

        val movimentacao = Movimentacao(
            id = id,
            valor = intent.getDoubleExtra(EXTRA_VALOR, 0.0),
            descricao = intent.getStringExtra(EXTRA_DESCRICAO).orEmpty(),
            data = intent.getLongExtra(EXTRA_DATA, System.currentTimeMillis()),
            tipo = TipoMovimentacao.valueOf(
                intent.getStringExtra(EXTRA_TIPO) ?: TipoMovimentacao.RECEITA.name
            )
        )

        viewModel.carregarEdicao(movimentacao)
        binding.tituloTela.setText(R.string.titulo_edicao)
        binding.campoCodigo.visibility = View.VISIBLE
        binding.entradaCodigo.setText(movimentacao.id.toString())
        binding.entradaValor.setText(movimentacao.valor.toString().replace('.', ','))
        binding.entradaDescricao.setText(movimentacao.descricao)
        binding.seletorTipo.check(
            if (movimentacao.tipo == TipoMovimentacao.RECEITA) R.id.opcaoReceita else R.id.opcaoDespesa
        )
        binding.botaoExcluir.visibility = View.VISIBLE
        binding.botaoVerExtrato.visibility = View.GONE
    }

    private fun configurarTipo() {
        binding.opcaoReceita.isChecked = true
        binding.seletorTipo.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener
            val tipo = if (checkedId == R.id.opcaoReceita) {
                TipoMovimentacao.RECEITA
            } else {
                TipoMovimentacao.DESPESA
            }
            viewModel.definirTipo(tipo)
        }
    }

    private fun configurarData() {
        binding.entradaData.setOnClickListener { abrirSeletorData() }
        binding.campoData.setEndIconOnClickListener { abrirSeletorData() }
    }

    private fun abrirSeletorData() {
        val seletor = MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.selecionar_data)
            .setSelection(viewModel.dataSelecionada.value)
            .build()
        seletor.addOnPositiveButtonClickListener { viewModel.definirData(it) }
        seletor.show(supportFragmentManager, "seletorData")
    }

    private fun configurarAcoes() {
        binding.botaoSalvar.setOnClickListener {
            viewModel.salvar(
                binding.entradaValor.text?.toString().orEmpty(),
                binding.entradaDescricao.text?.toString().orEmpty()
            )
        }
        binding.botaoExcluir.setOnClickListener { viewModel.excluir() }
        binding.botaoVerExtrato.setOnClickListener {
            startActivity(Intent(this, ExtratoActivity::class.java))
        }
    }

    private fun observarEstado() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.dataSelecionada.collect { millis ->
                        binding.entradaData.setText(Formatadores.data(millis))
                    }
                }
                launch {
                    viewModel.modoEdicao.collect { edicao ->
                        if (!edicao && intent.getLongExtra(EXTRA_ID, 0L) == 0L) {
                            binding.botaoVerExtrato.visibility = View.VISIBLE
                        }
                    }
                }
                launch {
                    viewModel.resultados.collect { tratarResultado(it) }
                }
            }
        }
    }

    private fun tratarResultado(resultado: ResultadoLancamento) {
        when (resultado) {
            is ResultadoLancamento.Salvo -> confirmarSalvo()
            is ResultadoLancamento.Excluido -> confirmarExcluido()
            is ResultadoLancamento.Invalido -> exibirErros(resultado.campos)
        }
    }

    private fun exibirErros(campos: List<CampoInvalido>) {
        binding.campoValor.error =
            if (CampoInvalido.VALOR in campos) getString(R.string.erro_valor) else null
        binding.campoDescricao.error =
            if (CampoInvalido.DESCRICAO in campos) getString(R.string.erro_descricao) else null
    }

    private fun confirmarSalvo() {
        if (viewModel.modoEdicao.value) {
            Snackbar.make(binding.root, R.string.lancamento_salvo, Snackbar.LENGTH_SHORT).show()
            finish()
            return
        }
        binding.campoValor.error = null
        binding.campoDescricao.error = null
        binding.entradaValor.text = null
        binding.entradaDescricao.text = null
        binding.opcaoReceita.isChecked = true
        Snackbar.make(binding.root, R.string.lancamento_salvo, Snackbar.LENGTH_SHORT).show()
    }

    private fun confirmarExcluido() {
        Snackbar.make(binding.root, R.string.lancamento_excluido, Snackbar.LENGTH_SHORT).show()
        finish()
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_VALOR = "extra_valor"
        const val EXTRA_DESCRICAO = "extra_descricao"
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_TIPO = "extra_tipo"

        fun intentEdicao(context: Context, movimentacao: Movimentacao): Intent {
            return Intent(context, LancamentoActivity::class.java).apply {
                putExtra(EXTRA_ID, movimentacao.id)
                putExtra(EXTRA_VALOR, movimentacao.valor)
                putExtra(EXTRA_DESCRICAO, movimentacao.descricao)
                putExtra(EXTRA_DATA, movimentacao.data)
                putExtra(EXTRA_TIPO, movimentacao.tipo.name)
            }
        }
    }
}
