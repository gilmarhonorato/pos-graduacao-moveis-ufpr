package com.exemplo.fluxocaixa.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LancamentoDbHelper(contexto: Context) : SQLiteOpenHelper(
    contexto,
    NOME_BANCO,
    null,
    VERSAO_BANCO
) {
    override fun onCreate(bancoDados: SQLiteDatabase) {
        bancoDados.execSQL(
            """
            CREATE TABLE $TABELA_LANCAMENTOS (
                $COLUNA_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUNA_VALOR REAL NOT NULL,
                $COLUNA_DESCRICAO TEXT NOT NULL,
                $COLUNA_DATA TEXT NOT NULL,
                $COLUNA_TIPO TEXT NOT NULL
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(bancoDados: SQLiteDatabase, versaoAntiga: Int, versaoNova: Int) {
        bancoDados.execSQL("DROP TABLE IF EXISTS $TABELA_LANCAMENTOS")
        onCreate(bancoDados)
    }

    companion object {
        private const val NOME_BANCO = "fluxo_caixa.db"
        private const val VERSAO_BANCO = 1
        const val TABELA_LANCAMENTOS = "lancamentos"
        const val COLUNA_ID = "id"
        const val COLUNA_VALOR = "valor"
        const val COLUNA_DESCRICAO = "descricao"
        const val COLUNA_DATA = "data_lancamento"
        const val COLUNA_TIPO = "tipo"
    }
}
