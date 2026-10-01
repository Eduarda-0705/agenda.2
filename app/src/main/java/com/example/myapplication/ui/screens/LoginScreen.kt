package com.example.myapplication.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.auth.AuthManager
import com.example.myapplication.ui.theme.AzulMarinho
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.Terracota

/**
 * Tela de login do app "Noted".
 *
 * Confere e-mail e senha no AuthManager. Se estiverem corretos chama
 * [loginSucesso] (a navegação para a Home é feita no AppNavigation); se não,
 * mostra a mensagem de erro na própria tela. O link "Cadastre-se" chama
 * [irCadastro]. Esta tela não mostra o Menu.
 */
@Composable
fun LoginScreen(
    loginSucesso: () -> Unit,
    irCadastro: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var senhaVisivel by remember { mutableStateOf(false) }
    var mensagemErro by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_feather),
            contentDescription = null,
            tint = Terracota,
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = "Noted",
            style = MaterialTheme.typography.headlineLarge,
            color = Terracota
        )
        Text(
            text = "Sua Agenda Simplificada",
            style = MaterialTheme.typography.bodyLarge,
            color = Terracota
        )

        Column(
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Entrar",
                style = MaterialTheme.typography.titleLarge,
                color = AzulMarinho,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = "E-mail", color = AzulMarinho)
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        mensagemErro = ""
                    },
                    isError = mensagemErro.isNotEmpty() && email.isBlank(),
                    label = { Text("Seu e-mail") },
                    leadingIcon = {
                        Icon(painterResource(id = R.drawable.ic_mail), contentDescription = null)
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = "Senha", color = AzulMarinho)
                OutlinedTextField(
                    value = senha,
                    onValueChange = {
                        senha = it
                        mensagemErro = ""
                    },
                    isError = mensagemErro.isNotEmpty() && senha.isBlank(),
                    label = { Text("Sua senha") },
                    leadingIcon = {
                        Icon(painterResource(id = R.drawable.ic_lock), contentDescription = null)
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(
                                id = if (senhaVisivel) R.drawable.ic_eye else R.drawable.ic_eye_off
                            ),
                            contentDescription = if (senhaVisivel) "Esconder senha" else "Mostrar senha",
                            modifier = Modifier.clickable { senhaVisivel = !senhaVisivel }
                        )
                    },
                    singleLine = true,
                    visualTransformation = if (senhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (mensagemErro.isNotEmpty()) {
                Text(text = mensagemErro, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = {
                    if (email.isBlank() || senha.isBlank()) {
                        mensagemErro = "Preencha e-mail e senha para continuar."
                    } else if (AuthManager.login(email, senha)) {
                        loginSucesso()
                    } else {
                        mensagemErro = "E-mail ou senha inválidos."
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = AzulMarinho),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "ACESSAR MINHA AGENDA")
            }

            Text(
                text = "Esqueceu a senha?",
                color = AzulMarinho,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Não tem uma conta? Cadastre-se",
                color = AzulMarinho,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { irCadastro() }
                    .padding(vertical = 8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    MyApplicationTheme {
        LoginScreen(loginSucesso = {}, irCadastro = {})
    }
}
