package com.example.myapplication.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.auth.AuthManager
import com.example.myapplication.ui.theme.AzulMarinho
import com.example.myapplication.ui.theme.Terracota

/**
 * Menu superior do app (aparece em todas as telas, menos Login e Cadastro).
 *
 * Home e Programado ficam sempre numa pilha "limpa": a Home é a base da
 * pilha e o Programado fica logo acima dela. Assim, apertar várias vezes os
 * botões do menu não empilha telas repetidas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(
    navController: NavHostController
) {
    TopAppBar(
        title = {
            Text(
                text = "Noted",
                color = Terracota,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        actions = {
            TextButton(onClick = {
                // Volta até a Home que já está na base da pilha.
                navController.popBackStack(route = Home, inclusive = false)
            }) {
                Text(text = "Home", color = AzulMarinho)
            }
            TextButton(onClick = {
                // Desempilha tudo que estiver acima da Home e abre o Programado.
                navController.navigate(Programado) {
                    popUpTo(Home) { inclusive = false }
                }
            }) {
                Text(text = "Programado", color = AzulMarinho)
            }
            IconButton(onClick = {
                AuthManager.logout()
                // A Home é a base da pilha: removê-la (inclusive) limpa tudo
                // e o botão "voltar" não consegue mais entrar no app.
                navController.navigate(Login) {
                    popUpTo(Home) { inclusive = true }
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logout),
                    contentDescription = "Sair",
                    tint = Terracota
                )
            }
        }
    )
}
