package com.duoc.nomeacuerdo.ui

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.duoc.nomeacuerdo.R
import com.duoc.nomeacuerdo.viewmodel.EventoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventosScreen(navController: NavController, viewModel: EventoViewModel = viewModel()) {
    val listaEventos by viewModel.eventos.collectAsState()
    var busqueda by remember { mutableStateOf("") }
    val eventosFiltrados = listaEventos.filter { it.titulo.contains(busqueda, ignoreCase = true) || it.descripcion.contains(busqueda, ignoreCase = true) }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(16.dp)) {
                Text(text = "Eventos", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text(text = "Descubre los mejores eventos disponibles cerca de ti.", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 12.dp))

                OutlinedTextField(
                    value = busqueda,
                    onValueChange = { busqueda = it },
                    label = { Text("Buscar Eventos") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                )
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(icon = { Icon(Icons.Default.Home, null) }, label = { Text("Inicio") }, selected = true, onClick = { })
                NavigationBarItem(icon = { Icon(Icons.Default.Star, null) }, label = { Text("Match") }, selected = false, onClick = { navController.navigate("match") { popUpTo("home") { inclusive = true } } })
                NavigationBarItem(icon = { Icon(Icons.Default.Person, null) }, label = { Text("Perfil") }, selected = false, onClick = { navController.navigate("perfil") })
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(eventosFiltrados) { evento ->
                Card(
                    modifier = Modifier.fillMaxWidth().clickable { navController.navigate("detalle_evento/${Uri.encode(evento.titulo)}") },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = painterResource(id = obtenerImagen(evento.imagen)), contentDescription = evento.titulo, modifier = Modifier.size(64.dp).padding(end = 16.dp))
                        Column {
                            Text(text = evento.titulo, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Horario: ${evento.hora}", fontSize = 14.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}

fun obtenerImagen(nombre: String): Int {
    return when (nombre.lowercase()) {
        "deporte" -> R.drawable.deporte
        "cine" -> R.drawable.cine
        "concierto" -> R.drawable.concierto
        else -> R.drawable.logo_mygos
    }
}