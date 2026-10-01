package com.example.myapplication.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.myapplication.model.Lembrete


object LembreteManager {

    // Só o LembreteManager pode trocar a lista (private set); as telas
    // apenas leem e chamam as funções abaixo.
    var lembretes by mutableStateOf(
        listOf(
            Lembrete(1, "Enviar Relatório Semanal", horario = "09:00 Ontem", grupo = "Atrasado"),
            Lembrete(2, "Ligar para João da Silva", horario = "14:30 Ontem", grupo = "Atrasado"),
            Lembrete(3, "Reunião de Equipe Semanal", concluido = true, horario = "11:00", grupo = "Hoje"),
            Lembrete(4, "Finalizar Apresentação Q3", horario = "16:00", grupo = "Hoje"),
            Lembrete(5, "Ir à Academia - Treino de Perna", horario = "18:00", grupo = "Hoje")
        )
    )
        private set

    fun adicionar(titulo: String, notas: String, horario: String, grupo: String) {
        val novoId = (lembretes.maxOfOrNull { it.id } ?: 0) + 1
        lembretes = lembretes + Lembrete(
            id = novoId,
            titulo = titulo.trim(),
            notas = notas.trim(),
            horario = horario,
            grupo = grupo
        )
    }

    fun alternarConcluido(id: Int) {
        lembretes = lembretes.map { item ->
            if (item.id == id) item.copy(concluido = !item.concluido) else item
        }
    }
}
