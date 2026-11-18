package com.duoc.nomeacuerdo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.duoc.nomeacuerdo.viewmodel.AuthViewModel
import com.duoc.nomeacuerdo.viewmodel.EventoViewModel
import com.duoc.nomeacuerdo.viewmodel.MatchViewModel

@Composable
fun DetalleEventoScreen(
    navController: NavController,
    tituloEvento: String,
    matchViewModel: MatchViewModel,
    authViewModel: AuthViewModel,
    eventoViewModel: EventoViewModel = viewModel()
) {

    val listaEventos by eventoViewModel.eventos.collectAsState()

    val evento = listaEventos.find {
        it.titulo == tituloEvento
    }

    val usuario by authViewModel.usuarioLogueado.collectAsState()

    var mostrarModal by remember {
        mutableStateOf(false)
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

        Text("Horario: ${evento?.hora ?: "No definido"}")

        Text("Dirección: ${evento?.direccion ?: "No definida"}")

        Text("Descripción: ${evento?.descripcion ?: "Sin descripción"}")

        Button(
            onClick = {
                if (usuario != null && evento != null) {

                    matchViewModel.agregarMatch(
                        tituloEvento,
                        evento.hora,
                        evento.direccion,
                        usuario!!.correo
                    )

                    mostrarModal = true
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Hacer Match")
        }
    }

    if (mostrarModal) {
        AlertDialog(
            onDismissRequest = {
                mostrarModal = false
            },
            title = {
                Text("¡Match Realizado!")
            },
            text = {
                Text("Has hecho match en el evento: $tituloEvento")
            },
            confirmButton = {
                Button(
                    onClick = {
                        mostrarModal = false

                        navController.navigate("match") {
                            popUpTo("home") {
                                inclusive = true
                            }
                        }
                    }
                ) {
                    Text("Ir a mis Matches")
                }
            }
        )
    }
}
