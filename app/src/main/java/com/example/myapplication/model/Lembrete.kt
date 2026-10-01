package com.example.myapplication.model

/**
 * Representa um lembrete/tarefa da agenda.
 *
 * @param id identificador único do lembrete
 * @param titulo título/nome do lembrete
 * @param concluido se o lembrete já foi marcado como concluído
 */
data class Lembrete(
    val id: Int,
    val titulo: String,
    val concluido: Boolean = false,
    val notas: String = "",
    val horario: String = "",
    val grupo: String = ""
)
