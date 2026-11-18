package com.duoc.nomeacuerdo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DetalleEventoScreen(navController: NavController, titulo: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        TextButton(
            onClick = { navController.popBackStack() }
        ) {
            Text("< Volver")
        }

        Text("Detalle del evento: $titulo")

        Button(
            onClick = { },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Hacer Match")
        }
    }
}