package com.example.myapplication.ui.screens

import android.R as AndroidR
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.data.LembreteManager
import com.example.myapplication.ui.theme.AzulMarinho
import com.example.myapplication.ui.theme.CinzaTexto
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.Terracota

@Composable
fun NovoLembreteScreen(
    modifier: Modifier = Modifier,
    voltar: () -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var notas by remember { mutableStateOf("") }
    var comData by remember { mutableStateOf(false) }
    var comHora by remember { mutableStateOf(false) }
    var sinalizado by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "Cancelar",
                tint = CinzaTexto,
                modifier = Modifier
                    .clickable { voltar() }
                    .padding(12.dp)
                    .size(24.dp)
            )
            Text(
                text = "Novo Lembrete",
                style = MaterialTheme.typography.titleLarge,
                color = Terracota,
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    LembreteManager.adicionar(
                        titulo = titulo,
                        notas = notas,
                        horario = if (comHora) "Hoje" else "",
                        grupo = "Hoje"
                    )
                    voltar()
                },
                // Só permite salvar se o título foi preenchido.
                enabled = titulo.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = AzulMarinho),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = "Concluído")
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            SecaoTitulo(texto = "Nota")
            Surface(modifier = Modifier.fillMaxWidth(), color = Color.White) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = titulo,
                        onValueChange = { titulo = it },
                        label = { Text("Título") },
                        leadingIcon = {
                            Icon(painterResource(id = R.drawable.ic_note_add), contentDescription = null)
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = notas,
                        onValueChange = { notas = it },
                        label = { Text("Notas") },
                        leadingIcon = {
                            Icon(painterResource(id = R.drawable.ic_notes), contentDescription = null)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            SecaoTitulo(texto = "Detalhes")
            Surface(modifier = Modifier.fillMaxWidth(), color = Color.White) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    LinhaAlternar(
                        icone = R.drawable.ic_calendar,
                        texto = "Data",
                        ativado = comData,
                        onClick = { comData = !comData }
                    )
                    LinhaAlternar(
                        icone = R.drawable.ic_clock,
                        texto = "Hora",
                        ativado = comHora,
                        onClick = { comHora = !comHora }
                    )
                    LinhaEstatica(icone = R.drawable.ic_list, texto = "Lista", valor = "Lembretes")
                }
            }

            SecaoTitulo(texto = "Opções")
            Surface(modifier = Modifier.fillMaxWidth(), color = Color.White) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    LinhaAlternar(
                        icone = R.drawable.ic_flag,
                        texto = "Sinalizar",
                        ativado = sinalizado,
                        onClick = { sinalizado = !sinalizado }
                    )
                    LinhaEstatica(icone = AndroidR.drawable.star_on, texto = "Prioridade", valor = "Nenhuma")
                }
            }
        }
    }
}

@Composable
private fun SecaoTitulo(texto: String) {
    Text(
        text = texto,
        color = Terracota,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
    )
}

@Composable
private fun LinhaAlternar(icone: Int, texto: String, ativado: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icone),
            contentDescription = null,
            tint = Terracota,
            modifier = Modifier.size(22.dp)
        )
        Text(
            text = texto,
            color = AzulMarinho,
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        )
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (ativado) AzulMarinho else CinzaTexto
            )
        ) {
            Text(text = if (ativado) "Ativado" else "Desativado")
        }
    }
}

@Composable
private fun LinhaEstatica(icone: Int, texto: String, valor: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icone),
            contentDescription = null,
            tint = Terracota,
            modifier = Modifier.size(22.dp)
        )
        Text(
            text = texto,
            color = AzulMarinho,
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        )
        Text(text = valor, color = CinzaTexto)
    }
}

@Preview(showBackground = true)
@Composable
private fun NovoLembreteScreenPreview() {
    MyApplicationTheme {
        NovoLembreteScreen(voltar = {})
    }
}
