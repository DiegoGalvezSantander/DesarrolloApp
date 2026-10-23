package com.duoc.nomeacuerdo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.duoc.nomeacuerdo.repository.EventoRepository
import com.duoc.nomeacuerdo.viewmodel.MatchViewModel

@Composable
fun DetalleEventoScreen(navController: NavController, tituloEvento: String, matchViewModel: MatchViewModel) {
    val participantes = listOf("A", "B", "C", "E", "F", "D")
    var mostrarModal by remember { mutableStateOf(false) }
    val evento = EventoRepository.listaEventos.find { it.titulo == tituloEvento }
    val correoPrueba = "test@duoc.cl"

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text("< Volver")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Detalle de evento", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Evento: $tituloEvento", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(text = "Horario: ${evento?.hora ?: "No definido"}", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = evento?.descripcion ?: "Sin descripción.")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Participantes", fontSize = 20.sp, modifier = Modifier.align(Alignment.Start))

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            participantes.forEach { letra ->
                Box(
                    modifier = Modifier.size(48.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = letra, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                matchViewModel.agregarMatch(tituloEvento, correoPrueba)
                mostrarModal = true
            },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Hacer Match", fontSize = 18.sp)
        }
    }

    if (mostrarModal) {
        AlertDialog(
            onDismissRequest = { mostrarModal = false },
            title = { Text("¡Match Realizado!") },
            text = { Text("Has hecho match en el evento: $tituloEvento") },
            confirmButton = {
                Button(onClick = {
                    mostrarModal = false
                    navController.navigate("match") { popUpTo("home") { inclusive = true } }
                }) {
                    Text("Ir a mis Matches")
                }
            }
        )
    }
}