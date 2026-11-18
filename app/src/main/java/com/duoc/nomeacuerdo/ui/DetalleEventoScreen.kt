package com.duoc.nomeacuerdo.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.duoc.nomeacuerdo.viewmodel.AuthViewModel
import com.duoc.nomeacuerdo.viewmodel.EventoViewModel
import com.duoc.nomeacuerdo.viewmodel.MatchViewModel

@Composable
fun DetalleEventoScreen(navController: NavController, tituloEvento: String, matchViewModel: MatchViewModel, authViewModel: AuthViewModel, eventoViewModel: EventoViewModel = viewModel()) {
    val listaEventos by eventoViewModel.eventos.collectAsState()
    val evento = listaEventos.find { it.titulo == tituloEvento }
    val usuario by authViewModel.usuarioLogueado.collectAsState()
    val participantes by matchViewModel.participantes.collectAsState()

    var mostrarModal by remember { mutableStateOf(false) }
    var isPressed by remember { mutableStateOf(false) }

    LaunchedEffect(tituloEvento) {
        matchViewModel.cargarParticipantes(tituloEvento)
    }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.1f else 1f,
        animationSpec = tween(durationMillis = 200),
        label = "scaleAnimation"
    )

    Column(modifier = Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        TextButton(onClick = { navController.popBackStack() }, modifier = Modifier.align(Alignment.Start)) { Text("< Volver a Eventos") }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(text = "Evento", fontSize = 14.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Text(text = tituloEvento, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Horario:", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = evento?.hora ?: "No definido", fontSize = 16.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Dirección:", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = evento?.direccion ?: "No definida", fontSize = 16.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Descripción:", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = evento?.descripcion ?: "Sin descripción", fontSize = 16.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Participantes (${participantes.size})", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Start))
        Spacer(modifier = Modifier.height(12.dp))

        if (participantes.isEmpty()) {
            Text(text = "Sé el primero en hacer match en este evento.", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.align(Alignment.Start))
        } else {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(participantes) { participante ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            val uriFoto = participante.foto_perfil_uri
                            if (!uriFoto.isNullOrBlank()) {
                                AsyncImage(
                                    model = uriFoto,
                                    contentDescription = participante.nombre_completo,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                Text(text = participante.nombre_completo.take(1).uppercase(), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = participante.nombre_completo.split(" ").firstOrNull() ?: "User", fontSize = 12.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (usuario != null && evento != null) {
                    isPressed = true
                    matchViewModel.agregarMatch(tituloEvento, evento.hora, evento.direccion, usuario!!.correo)
                    mostrarModal = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .scale(scale),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Hacer Match", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }

    if (mostrarModal) {
        AlertDialog(
            onDismissRequest = { mostrarModal = false; isPressed = false },
            title = { Text("¡Match Realizado!") },
            text = { Text("Te has registrado con éxito en el evento: $tituloEvento") },
            confirmButton = {
                Button(onClick = {
                    mostrarModal = false
                    isPressed = false
                    navController.navigate("match") { popUpTo("home") { inclusive = true } }
                }) { Text("Ir a mis Matches") }
            }
        )
    }
}