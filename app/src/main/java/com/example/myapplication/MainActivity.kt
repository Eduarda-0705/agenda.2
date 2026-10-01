package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.AppNavigation
import com.example.myapplication.navigation.Menu
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                // Descobre em qual tela o usuário está agora.
                val pilhaRotas by navController.currentBackStackEntryAsState()
                val rotaAtual = pilhaRotas?.destination?.route

                // O menu só aparece fora das telas de Login e Cadastro.
                val mostrarMenu = rotaAtual != null &&
                        !rotaAtual.contains("Login") &&
                        !rotaAtual.contains("Cadastro")

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (mostrarMenu) {
                            Menu(navController = navController)
                        }
                    }
                ) { innerPadding ->
                    AppNavigation(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
