package com.exemplo.usandodb.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.exemplo.usandodb.entity.cadastro

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(bd: SQLiteDatabase?) {
        bd?.execSQL( "CREATE TABLE IF NOT EXISTS " +
                "${TABLE_NAME}( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT, telefone TEXT) "
        )
    }

    override fun onUpgrade(
        bd: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        bd?.execSQL( "DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(bd)
    }


    fun incluir(cadastro: cadastro) {

        val banco = this.writableDatabase

        val registro = ContentValues()
        registro.put( "nome", cadastro.nome )
        registro.put( "telefone", cadastro.telefone )

        banco.insert( TABLE_NAME, null, registro )
        banco.close()
    }

    fun alterar(cadastro: cadastro) {

        val banco = this.writableDatabase

        val registro = ContentValues()
        registro.put( "nome", cadastro.nome )
        registro.put( "telefone", cadastro.telefone )

        banco.update(
            TABLE_NAME,
            registro,
            "_id = ${cadastro.id}",
            null
        )

        banco.close()
    }

    fun excluir(id: Int) {

        val banco = this.writableDatabase

        banco.delete(
            TABLE_NAME,
            "_id = ${id}",
            null
        )

        banco.close()
    }

    fun pesquisar(id: Int): cadastro? {

        val banco = this.readableDatabase

        val registros = banco.query(
            TABLE_NAME,
            null,
            "_id = ${id}",
            null,
            null,
            null,
            null
        )

        if( registros.moveToNext() ) {
            val registro = cadastro(
                id = registros.getInt( ID ),
                nome = registros.getString( NOME ),
                telefone = registros.getString( TELEFONE )
            )
            registros.close()
            banco.close()
            return registro
        } else {
            registros.close()
            banco.close()
            return null
        }
    }

    fun listar(): MutableList<cadastro> {

        val banco = this.readableDatabase
        val saida = mutableListOf<cadastro>()

        val registros = banco.query(
            TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        while( registros.moveToNext() ) {

            val registro = cadastro(
                id = registros.getInt(ID),
                nome = registros.getString(NOME),
                telefone = registros.getString(TELEFONE)
            )

            saida.add( registro  )
        }

        registros.close()
        banco.close()

        return saida
    }

    fun listarCursor(): Cursor {

        val banco = this.readableDatabase

        val registros = banco.query(
            TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        return registros
    }



    companion object {
        private const val DATABASE_NAME = "banco.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "cadastro"
        private const val ID = 0
        private const val NOME = 1
        private const val TELEFONE = 2
    }

} //fim do DatabaseHandler

