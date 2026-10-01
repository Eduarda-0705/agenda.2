package com.example.myapplication.model


data class Lembrete(
    val id: Int,
    val titulo: String,
    val concluido: Boolean = false,
    val notas: String = "",
    val horario: String = "",
    val grupo: String = ""
)
