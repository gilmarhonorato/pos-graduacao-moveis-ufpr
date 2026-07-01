package com.exemplo.fluxocaixa.database

import android.content.ContentValues
import android.content.Context
import com.exemplo.fluxocaixa.model.Lancamento
import com.exemplo.fluxocaixa.model.TipoLancamento

class LancamentoRepositorio(contexto: Context) {

    private val bancoHelper = LancamentoDbHelper(contexto.applicationContext)

    fun salvarLancamento(lancamento: Lancamento): Long {
        val valores = ContentValues().apply {
            put(LancamentoDbHelper.COLUNA_VALOR, lancamento.valor)
            put(LancamentoDbHelper.COLUNA_DESCRICAO, lancamento.descricao)
            put(LancamentoDbHelper.COLUNA_DATA, lancamento.dataLancamento)
            put(LancamentoDbHelper.COLUNA_TIPO, lancamento.tipoLancamento.name)
        }
        val bancoDados = bancoHelper.writableDatabase
        return bancoDados.insert(LancamentoDbHelper.TABELA_LANCAMENTOS, null, valores)
    }

    fun listarLancamentos(): List<Lancamento> {
        val bancoDados = bancoHelper.readableDatabase
        val cursor = bancoDados.query(
            LancamentoDbHelper.TABELA_LANCAMENTOS,
            null,
            null,
            null,
            null,
            null,
            "${LancamentoDbHelper.COLUNA_DATA} DESC, ${LancamentoDbHelper.COLUNA_ID} DESC"
        )
        val listaLancamentos = mutableListOf<Lancamento>()
        cursor.use {
            val indiceId = it.getColumnIndexOrThrow(LancamentoDbHelper.COLUNA_ID)
            val indiceValor = it.getColumnIndexOrThrow(LancamentoDbHelper.COLUNA_VALOR)
            val indiceDescricao = it.getColumnIndexOrThrow(LancamentoDbHelper.COLUNA_DESCRICAO)
            val indiceData = it.getColumnIndexOrThrow(LancamentoDbHelper.COLUNA_DATA)
            val indiceTipo = it.getColumnIndexOrThrow(LancamentoDbHelper.COLUNA_TIPO)
            while (it.moveToNext()) {
                listaLancamentos.add(
                    Lancamento(
                        id = it.getLong(indiceId),
                        valor = it.getDouble(indiceValor),
                        descricao = it.getString(indiceDescricao),
                        dataLancamento = it.getString(indiceData),
                        tipoLancamento = TipoLancamento.valueOf(it.getString(indiceTipo))
                    )
                )
            }
        }
        return listaLancamentos
    }

    fun calcularSaldoTotal(): Double {
        return listarLancamentos().sumOf { lancamento ->
            if (lancamento.tipoLancamento.ehReceita()) lancamento.valor else -lancamento.valor
        }
    }
}
