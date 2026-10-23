package com.duoc.nomeacuerdo.ui

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.duoc.nomeacuerdo.viewmodel.AuthViewModel
import com.duoc.nomeacuerdo.viewmodel.MatchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchScreen(navController: NavController, viewModel: MatchViewModel, authViewModel: AuthViewModel) {
    val listaMatches by viewModel.matches.collectAsState()
    val usuario by authViewModel.usuarioLogueado.collectAsState()

    LaunchedEffect(usuario) {
        if (usuario != null) {
            viewModel.cargarMatches(usuario!!.correo)
        }
    }

    Scaffold(
        topBar = { Column(modifier = Modifier.padding(16.dp)) { Text("Mis Matches", fontSize = 28.sp, fontWeight = FontWeight.Bold) } },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(icon = { Icon(Icons.Default.Home, null) }, label = { Text("Inicio") }, selected = false, onClick = { navController.navigate("home") { popUpTo("home") { inclusive = true } } })
                NavigationBarItem(icon = { Icon(Icons.Default.Star, null) }, label = { Text("Match") }, selected = true, onClick = { })
                NavigationBarItem(icon = { Icon(Icons.Default.Person, null) }, label = { Text("Perfil") }, selected = false, onClick = { })
            }
        }
    ) { paddingValues ->
        if (listaMatches.isEmpty()) {
            Column(modifier = Modifier.fillMaxSize().padding(paddingValues), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text("Aún no tienes matches confirmados.")
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(listaMatches) { match ->
                    Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Evento: ${match.nombreEvento}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Fecha: ${match.fechaMatch}", fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}