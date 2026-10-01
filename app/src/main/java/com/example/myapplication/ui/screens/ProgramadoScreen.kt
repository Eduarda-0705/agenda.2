package com.example.myapplication.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.myapplication.data.LembreteManager
import com.example.myapplication.model.Lembrete
import com.example.myapplication.ui.theme.AzulMarinho
import com.example.myapplication.ui.theme.CinzaClaro
import com.example.myapplication.ui.theme.CinzaTexto
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.Terracota

/**
 * Tela "Programado": mostra os lembretes agrupados por "Atrasado" e "Hoje".
 *
 * Usa a lista do LembreteManager, então um lembrete criado na tela Novo
 * Lembrete aparece aqui, e marcar/desmarcar como concluído atualiza também
 * os totais da Home.
 *
 * @param voltar volta para a tela anterior (a Home)
 * @param irParaNovoLembrete abre a tela Novo Lembrete
 */
@Composable
fun ProgramadoScreen(
    modifier: Modifier = Modifier,
    voltar: () -> Unit,
    irParaNovoLembrete: () -> Unit
) {
    val lembretes = LembreteManager.lembretes
    val atrasados = lembretes.filter { it.grupo == "Atrasado" }
    val deHoje = lembretes.filter { it.grupo == "Hoje" }

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Voltar",
                tint = Terracota,
                modifier = Modifier
                    .clickable { voltar() }
                    .padding(12.dp)
            )
            Text(
                text = "Programado",
                style = MaterialTheme.typography.titleLarge,
                color = Terracota
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    if (atrasados.isNotEmpty()) {
                        Text(
                            text = "Atrasado",
                            color = Terracota,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        atrasados.forEach { lembrete ->
                            LinhaLembrete(
                                lembrete = lembrete,
                                onClick = { LembreteManager.alternarConcluido(lembrete.id) }
                            )
                        }
                    }

                    if (deHoje.isNotEmpty()) {
                        Text(
                            text = "Hoje",
                            color = AzulMarinho,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                        )
                        deHoje.forEach { lembrete ->
                            LinhaLembrete(
                                lembrete = lembrete,
                                onClick = { LembreteManager.alternarConcluido(lembrete.id) }
                            )
                        }
                    }

                    if (atrasados.isEmpty() && deHoje.isEmpty()) {
                        Text(text = "Nenhum lembrete programado.", color = CinzaTexto)
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
private fun LinhaLembrete(lembrete: Lembrete, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (lembrete.concluido) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check_circle),
                contentDescription = null,
                tint = Terracota,
                modifier = Modifier.size(22.dp)
            )
        } else {
            Surface(
                modifier = Modifier.size(20.dp),
                color = CinzaClaro
            ) {}
        }
        Column(modifier = Modifier.padding(start = 12.dp)) {
            Text(
                text = lembrete.titulo,
                color = if (lembrete.grupo == "Atrasado") Terracota else AzulMarinho,
                fontWeight = FontWeight.Medium
            )
            if (lembrete.horario.isNotEmpty()) {
                Text(text = lembrete.horario, color = CinzaTexto, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProgramadoScreenPreview() {
    MyApplicationTheme {
        ProgramadoScreen(voltar = {}, irParaNovoLembrete = {})
    }
}
