package com.exemplo.myapplication.data

import android.content.Context

object Dependencias {

    fun repositorio(context: Context): MovimentacaoRepository =
        MovimentacaoRepository(AppDatabase.obter(context).movimentacaoDao())
}
