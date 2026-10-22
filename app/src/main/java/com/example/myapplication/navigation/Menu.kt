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
import com.example.myapplication.auth.SessaoUsuario
import com.example.myapplication.ui.theme.AzulMarinho
import com.example.myapplication.ui.theme.Terracota

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
                navController.popBackStack(route = Home, inclusive = false)
            }) {
                Text(text = "Home", color = AzulMarinho)
            }
            TextButton(onClick = {
                navController.navigate(Programado) {
                    popUpTo(Home) { inclusive = false }
                }
            }) {
                Text(text = "Programado", color = AzulMarinho)
            }
            IconButton(onClick = {
                SessaoUsuario.encerrar()
                navController.navigate(Login) {
                    popUpTo(0) { inclusive = true }
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
