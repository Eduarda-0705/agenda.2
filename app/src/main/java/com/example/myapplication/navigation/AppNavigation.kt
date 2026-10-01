package com.example.myapplication.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.screens.CadastroScreen
import com.example.myapplication.ui.screens.HomeScreen
import com.example.myapplication.ui.screens.LoginScreen
import com.example.myapplication.ui.screens.NovoLembreteScreen
import com.example.myapplication.ui.screens.ProgramadoScreen


@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Login
    ) {
        composable<Login> {
            LoginScreen(
                loginSucesso = {
                    // Entra na Home e remove o Login da pilha, para o botão
                    // "voltar" não retornar à tela de login.
                    navController.navigate(Home) {
                        popUpTo(Login) { inclusive = true }
                    }
                },
                irCadastro = { navController.navigate(Cadastro) }
            )
        }

        composable<Cadastro> {
            CadastroScreen(
                irLogin = { navController.popBackStack() }
            )
        }

        composable<Home> {
            HomeScreen(
                modifier = modifier,
                irParaProgramado = { navController.navigate(Programado) },
                irParaNovoLembrete = { navController.navigate(NovoLembrete) }
            )
        }

        composable<Programado> {
            ProgramadoScreen(
                modifier = modifier,
                voltar = { navController.popBackStack() },
                irParaNovoLembrete = { navController.navigate(NovoLembrete) }
            )
        }

        composable<NovoLembrete>(
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(500)
                )
            }
        ) {
            NovoLembreteScreen(
                modifier = modifier,
                voltar = { navController.popBackStack() }
            )
        }
    }
}
