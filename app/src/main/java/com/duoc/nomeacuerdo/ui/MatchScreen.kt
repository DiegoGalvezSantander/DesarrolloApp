package com.duoc.nomeacuerdo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchScreen(navController: NavController) {
    Scaffold(
        topBar = {
            Text(
                "Mis Matches",
                fontSize = 28.sp,
                modifier = Modifier.padding(16.dp)
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Inicio") },
                    selected = false,
                    onClick = {
                        navController.navigate("home") {
                            popUpTo("home") {
                                inclusive = true
                            }
                        }
                    }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Star, null) },
                    label = { Text("Match") },
                    selected = true,
                    onClick = { }
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(
                "Lista de matches aquí",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}