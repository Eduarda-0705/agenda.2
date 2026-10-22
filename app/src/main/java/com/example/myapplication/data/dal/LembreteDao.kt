package com.example.myapplication.data.dal

import android.content.ContentValues
import android.content.Context
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.model.Lembrete

internal class LembreteDao(context: Context) {
    private val appContext = context.applicationContext

    fun cadastrar(usuarioId: Long,
                  titulo: String,
                  notas: String,
                  data: String,
                  horario: String,
                  sinalizado: Boolean
    ){
        AppDatabase(appContext).use { db ->
            val dados = ContentValues().apply {
                put("usuario_id", usuarioId)
                put("titulo", titulo)
                put("notas", notas)
                put("data", data)
                put("horario", horario)
                put("sinalizado", if (sinalizado) 1 else 0)
                put("concluido", 0)
            }
            db.writableDatabase
                .insertOrThrow("lembretes",
                    null,
                    dados)
        }
    }

    fun listar(usuarioId: Long): List<Lembrete> = AppDatabase(appContext).use {
        db ->
        db.readableDatabase.query(
            "lembretes",
            arrayOf("id", "titulo", "notas", "data", "horario", "sinalizado", "concluido"),
            "usuario_id = ?",
            arrayOf(usuarioId.toString()),
            null, null,
            "concluido, id DESC"
        ).use { cursor ->
            buildList {
                while (cursor.moveToNext()){
                    add(Lembrete(cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5) == 1,
                        cursor.getInt(6) == 1))
                }
            }
        }
    }

    fun buscarPorId(id: Long, usuarioId: Long): Lembrete? = AppDatabase(appContext).use {
        db ->
        db.readableDatabase.query(
            "lembretes",
            arrayOf("id", "titulo", "notas", "data", "horario", "sinalizado", "concluido"),
            "id = ? AND usuario_id = ?",
            arrayOf(id.toString(), usuarioId.toString()),
            null, null, null
        ).use { cursor ->
            if (cursor.moveToFirst()) Lembrete(cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getInt(5) == 1,
                cursor.getInt(6) == 1)
            else null
        }
    }

    fun alterar(id: Long,
                usuarioId: Long,
                titulo: String,
                notas: String,
                data: String,
                horario: String,
                sinalizado: Boolean
    ){
        AppDatabase(appContext).use { db ->
            val dados = ContentValues().apply {
                put("titulo", titulo)
                put("notas", notas)
                put("data", data)
                put("horario", horario)
                put("sinalizado", if (sinalizado) 1 else 0)
            }
            val alterados = db.writableDatabase.update(
                "lembretes",
                dados,
                "id = ? AND usuario_id = ?",
                arrayOf(id.toString(), usuarioId.toString()))
            require(alterados == 1) { "Erro ao alterar o lembrete." }
        }
    }

    fun alterarConcluido(id: Long, usuarioId: Long, concluido: Boolean){
        AppDatabase(appContext).use { db ->
            val dados = ContentValues().apply {
                put("concluido", if (concluido) 1 else 0)
            }
            val alterados = db.writableDatabase.update(
                "lembretes",
                dados,
                "id = ? AND usuario_id = ?",
                arrayOf(id.toString(), usuarioId.toString()))
            require(alterados == 1) { "Erro ao alterar o lembrete." }
        }
    }

    fun excluir(id: Long, usuarioId: Long){
        AppDatabase(appContext).use { db ->
            val excluidos = db.writableDatabase.delete(
                "lembretes", "id = ? AND usuario_id = ?",
                arrayOf(id.toString(), usuarioId.toString())
            )
            require(excluidos == 1) { "Lembrete não pode ser excluído." }
        }
    }

}
