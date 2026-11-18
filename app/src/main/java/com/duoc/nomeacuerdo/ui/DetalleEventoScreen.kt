
package com.duoc.nomeacuerdo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.duoc.nomeacuerdo.viewmodel.EventoViewModel

@Composable
fun DetalleEventoScreen(
    navController: NavController,
    tituloEvento: String,
    eventoViewModel: EventoViewModel = viewModel()
) {

    val listaEventos by eventoViewModel.eventos.collectAsState()

    val evento = listaEventos.find {
        it.titulo == tituloEvento
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        TextButton(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text("< Volver")
        }

        Text(
            "Evento: $tituloEvento",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            "Horario: ${evento?.hora ?: "No definido"}"
        )

        Text(
            "Dirección: ${evento?.direccion ?: "No definida"}"
        )

        Text(
            "Descripción: ${evento?.descripcion ?: "Sin descripción"}"
        )

        Button(
            onClick = { },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Hacer Match")
        }
    }
}
