package com.example.myapplication.auth


object AuthManager {
    data class User(
        val email: String,
        val senha: String,
        val nome: String
    )

    private val usuarios = mutableListOf<User>()

    var usuarioLogado: User? = null
        private set

    fun login(email: String, senha: String): Boolean {
        val emailLimpo = email.trim().lowercase()
        val usuario = usuarios.find { it.email == emailLimpo && it.senha == senha }
        usuarioLogado = usuario
        return usuario != null
    }

    fun cadastro(nome: String, email: String, senha: String): Boolean {
        val emailLimpo = email.trim().lowercase()
        if (usuarios.any { it.email == emailLimpo }) return false

        usuarios.add(User(emailLimpo, senha, nome.trim()))
        return true
    }

    fun logout() {
        usuarioLogado = null
    }
}
