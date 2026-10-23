package com.duoc.nomeacuerdo.ui

import android.net.Uri
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.duoc.nomeacuerdo.repository.EventoRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventosScreen(navController: NavController) {
    val listaEventos = EventoRepository.listaEventos
    var busqueda by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Eventos", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = busqueda,
                    onValueChange = { busqueda = it },
                    label = { Text("Buscar Eventos") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio") },
                    selected = true,
                    onClick = { }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Star, contentDescription = "Match") },
                    label = { Text("Match") },
                    selected = false,
                    onClick = { }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                    label = { Text("Perfil") },
                    selected = false,
                    onClick = { navController.navigate("perfil") }
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(listaEventos) { evento ->
                Card(
                    modifier = Modifier.fillMaxWidth().clickable {
                        navController.navigate("detalle_evento/${Uri.encode(evento.titulo)}")
                    },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = evento.titulo, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(text = evento.hora, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}