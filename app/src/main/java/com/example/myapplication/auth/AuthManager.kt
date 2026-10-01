package com.example.myapplication.auth

/**
 * Controle simples de usuários (aula 11).
 *
 * É um `object` (um único objeto para o app inteiro, como o "static" do Java)
 * que guarda os usuários cadastrados numa lista em memória. Por isso os
 * cadastros se perdem quando o app é fechado.
 */
object AuthManager {
    data class User(
        val email: String,
        val senha: String,
        val nome: String
    )

    private val usuarios = mutableListOf<User>()

    // Quem está logado agora. Só o AuthManager pode alterar (private set,
    // igual à senha da classe Usuario vista na aula de encapsulamento).
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
