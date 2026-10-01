package com.example.myapplication.ui.screens

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
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

@Composable
fun CadastroScreen(
    irLogin: () -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var senhaVisivel by remember { mutableStateOf(false) }
    var mensagemErro by remember { mutableStateOf("") }
    val context = LocalContext.current

    val nomeInvalido = nome.isNotEmpty() && nome.trim().length < 3
    val emailInvalido = email.isNotEmpty() && !(email.contains("@") && email.contains("."))
    val senhaInvalida = senha.isNotEmpty() && senha.length < 6

    val formularioValido = nome.trim().length >= 3 &&
            email.contains("@") && email.contains(".") &&
            senha.length >= 6

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
                text = "Criar conta",
                style = MaterialTheme.typography.titleLarge,
                color = AzulMarinho,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = "Nome", color = AzulMarinho)
                OutlinedTextField(
                    value = nome,
                    onValueChange = {
                        nome = it
                        mensagemErro = ""
                    },
                    isError = nomeInvalido,
                    label = { Text("Seu nome completo") },
                    leadingIcon = {
                        Icon(painterResource(id = R.drawable.ic_user), contentDescription = null)
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                if (nomeInvalido) {
                    Text(
                        text = "O nome precisa conter pelo menos 3 caracteres.",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = "E-mail", color = AzulMarinho)
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        mensagemErro = ""
                    },
                    isError = emailInvalido,
                    label = { Text("Seu e-mail") },
                    leadingIcon = {
                        Icon(painterResource(id = R.drawable.ic_mail), contentDescription = null)
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )
                if (emailInvalido) {
                    Text(
                        text = "Digite um e-mail válido (ex.: nome@email.com).",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = "Senha", color = AzulMarinho)
                OutlinedTextField(
                    value = senha,
                    onValueChange = {
                        senha = it
                        mensagemErro = ""
                    },
                    isError = senhaInvalida,
                    label = { Text("Crie uma senha") },
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
                if (senhaInvalida) {
                    Text(
                        text = "A senha precisa ter pelo menos 6 caracteres.",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            if (mensagemErro.isNotEmpty()) {
                Text(text = mensagemErro, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = {
                    if (AuthManager.cadastro(nome, email, senha)) {
                        Toast.makeText(
                            context,
                            "Cadastro realizado com sucesso! Faça seu login.",
                            Toast.LENGTH_SHORT
                        ).show()
                        irLogin()
                    } else {
                        mensagemErro = "Este e-mail já está cadastrado."
                    }
                },
                enabled = formularioValido,
                colors = ButtonDefaults.buttonColors(containerColor = AzulMarinho),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "CRIAR MINHA CONTA")
            }

            Text(
                text = "Já tem uma conta? Entrar",
                color = AzulMarinho,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { irLogin() }
                    .padding(vertical = 8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CadastroScreenPreview() {
    MyApplicationTheme {
        CadastroScreen(irLogin = {})
    }
}
