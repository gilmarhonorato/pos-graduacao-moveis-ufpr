package com.exemplo.myapplication.data

import androidx.room.TypeConverter

class Conversores {

    @TypeConverter
    fun tipoParaTexto(tipo: TipoMovimentacao): String = tipo.name

    @TypeConverter
    fun textoParaTipo(texto: String): TipoMovimentacao = TipoMovimentacao.valueOf(texto)
}
