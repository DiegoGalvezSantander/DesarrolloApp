package com.duoc.nomeacuerdo.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventosScreen(navController: NavController) {
    val dummyEventos = listOf("Deporte", "Cine", "Concierto")

    Scaffold(
        topBar = {
            Text(
                "Eventos",
                fontSize = 28.sp,
                modifier = Modifier.padding(16.dp)
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Inicio") },
                    selected = true,
                    onClick = { }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Star, null) },
                    label = { Text("Match") },
                    selected = false,
                    onClick = {
                        navController.navigate("match") {
                            popUpTo("home") {
                                inclusive = true
                            }
                        }
                    }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("Perfil") },
                    selected = false,
                    onClick = {
                        navController.navigate("perfil")
                    }
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(dummyEventos) { evento ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("detalle_evento/$evento")
                        }
                ) {
                    Text(
                        text = evento,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}