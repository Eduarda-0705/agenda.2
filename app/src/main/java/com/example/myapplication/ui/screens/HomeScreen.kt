package com.example.myapplication.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.auth.AuthManager
import com.example.myapplication.data.LembreteManager
import com.example.myapplication.ui.theme.AzulCartao
import com.example.myapplication.ui.theme.AzulMarinho
import com.example.myapplication.ui.theme.CinzaCartao
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.Terracota
import com.example.myapplication.ui.theme.VermelhoCartao


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    irParaProgramado: () -> Unit,
    irParaNovoLembrete: () -> Unit
) {
    val lembretes = LembreteManager.lembretes

    val totalHoje = lembretes.count { it.grupo == "Hoje" }
    val totalProgramado = lembretes.count { it.grupo == "Atrasado" || it.grupo == "Hoje" }
    val totalTodos = lembretes.size
    val totalConcluidos = lembretes.count { it.concluido }

    // Nome informado no cadastro; "Visitante" só aparece se não houver usuário.
    val nomeUsuario = AuthManager.usuarioLogado?.nome ?: "Visitante"

    Column(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "Olá, $nomeUsuario!",
                style = MaterialTheme.typography.titleLarge,
                color = AzulMarinho,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                CartaoResumo(
                    titulo = "Hoje",
                    quantidade = totalHoje,
                    icone = R.drawable.ic_calendar,
                    corFundo = AzulCartao,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 6.dp)
                )
                CartaoResumo(
                    titulo = "Programado",
                    quantidade = totalProgramado,
                    icone = R.drawable.ic_calendar,
                    corFundo = VermelhoCartao,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 6.dp),
                    onClick = irParaProgramado
                )
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp)) {
                CartaoResumo(
                    titulo = "Tudo",
                    quantidade = totalTodos,
                    icone = R.drawable.ic_inbox,
                    corFundo = CinzaCartao,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 6.dp)
                )
                CartaoResumo(
                    titulo = "Concluído",
                    quantidade = totalConcluidos,
                    icone = R.drawable.ic_check_circle,
                    corFundo = AzulMarinho,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 6.dp)
                )
            }

            Surface(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                color = Color.White
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Minhas Listas",
                        style = MaterialTheme.typography.titleMedium,
                        color = Terracota
                    )
                    Row(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                            .clickable { irParaProgramado() },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier.size(28.dp),
                            color = Terracota
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_list),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .size(16.dp)
                                )
                            }
                        }
                        Text(
                            text = "Lembretes",
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .weight(1f),
                            color = AzulMarinho
                        )
                        Text(text = "$totalTodos", color = AzulMarinho)
                        Icon(
                            painter = painterResource(id = R.drawable.ic_chevron_right),
                            contentDescription = null,
                            tint = AzulMarinho
                        )
                    }
                }
            }
        }

        Button(
            onClick = irParaNovoLembrete,
            colors = ButtonDefaults.buttonColors(containerColor = AzulMarinho),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(text = "+ NOVO LEMBRETE")
        }
    }
}

@Composable
private fun CartaoResumo(
    titulo: String,
    quantidade: Int,
    icone: Int,
    corFundo: Color,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Surface(
        modifier = modifier
            .height(90.dp)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        color = corFundo
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(id = icone),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
                Text(text = "$quantidade", color = Color.White, style = MaterialTheme.typography.titleLarge)
            }
            Text(
                text = titulo,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    MyApplicationTheme {
        HomeScreen(irParaProgramado = {}, irParaNovoLembrete = {})
    }
}
