package com.exemplo.myapplication.ui.lancamento

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exemplo.myapplication.data.Movimentacao
import com.exemplo.myapplication.data.MovimentacaoRepository
import com.exemplo.myapplication.data.TipoMovimentacao
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LancamentoViewModel(
    private val repositorio: MovimentacaoRepository
) : ViewModel() {

    private var idEdicao: Long = 0

    private val _dataSelecionada = MutableStateFlow(System.currentTimeMillis())
    val dataSelecionada: StateFlow<Long> = _dataSelecionada

    private val _tipoSelecionado = MutableStateFlow(TipoMovimentacao.RECEITA)
    val tipoSelecionado: StateFlow<TipoMovimentacao> = _tipoSelecionado

    private val _modoEdicao = MutableStateFlow(false)
    val modoEdicao: StateFlow<Boolean> = _modoEdicao

    private val _resultados = Channel<ResultadoLancamento>(Channel.BUFFERED)
    val resultados = _resultados.receiveAsFlow()

    fun carregarEdicao(movimentacao: Movimentacao) {
        idEdicao = movimentacao.id
        _modoEdicao.value = true
        _dataSelecionada.value = movimentacao.data
        _tipoSelecionado.value = movimentacao.tipo
    }

    fun definirData(epochMillis: Long) {
        _dataSelecionada.value = epochMillis
    }

    fun definirTipo(tipo: TipoMovimentacao) {
        _tipoSelecionado.value = tipo
    }

    fun salvar(valorTexto: String, descricao: String) {
        val valor = valorTexto.replace(',', '.').toDoubleOrNull()
        val descricaoLimpa = descricao.trim()

        val erros = mutableListOf<CampoInvalido>()
        if (valor == null || valor <= 0.0) erros += CampoInvalido.VALOR
        if (descricaoLimpa.isEmpty()) erros += CampoInvalido.DESCRICAO

        if (erros.isNotEmpty()) {
            _resultados.trySend(ResultadoLancamento.Invalido(erros))
            return
        }

        viewModelScope.launch {
            repositorio.salvar(
                Movimentacao(
                    id = idEdicao,
                    valor = valor!!,
                    descricao = descricaoLimpa,
                    data = _dataSelecionada.value,
                    tipo = _tipoSelecionado.value
                )
            )
            _resultados.trySend(ResultadoLancamento.Salvo)
        }
    }

    fun excluir() {
        if (idEdicao == 0L) return
        viewModelScope.launch {
            repositorio.excluir(idEdicao)
            _resultados.trySend(ResultadoLancamento.Excluido)
        }
    }
}
