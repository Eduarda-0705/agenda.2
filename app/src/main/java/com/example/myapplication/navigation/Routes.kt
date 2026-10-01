package com.example.myapplication.navigation

import kotlinx.serialization.Serializable

// Cada tela do app tem uma rota (aula 10).
// Como nenhuma delas recebe parâmetros, todas são `data object`.

@Serializable
data object Login

@Serializable
data object Cadastro

@Serializable
data object Home

@Serializable
data object Programado

@Serializable
data object NovoLembrete
