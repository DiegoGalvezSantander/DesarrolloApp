package com.duoc.nomeacuerdo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.duoc.nomeacuerdo.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(navController: NavController, viewModel: AuthViewModel) {
    val nombre by viewModel.regNombre.collectAsState()
    val correo by viewModel.regCorreo.collectAsState()
    val password by viewModel.regPassword.collectAsState()
    val error by viewModel.regError.collectAsState()
    val usuarioLogueado by viewModel.usuarioLogueado.collectAsState()

    LaunchedEffect(usuarioLogueado) {
        if (usuarioLogueado != null) {
            navController.navigate("home") { popUpTo("login") { inclusive = true } }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "ÚNETE A MYGOS", fontSize = 28.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 24.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { viewModel.onRegNombreChange(it) },
            label = { Text("NOMBRE Y APELLIDO") },
            modifier = Modifier.fillMaxWidth(),
            isError = error != null
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { viewModel.onRegCorreoChange(it) },
            label = { Text("EMAIL") },
            modifier = Modifier.fillMaxWidth(),
            isError = error != null
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.onRegPasswordChange(it) },
            label = { Text("CONTRASEÑA") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            isError = error != null,
            supportingText = { if (error != null) Text(text = error!!, color = MaterialTheme.colorScheme.error) }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { viewModel.registrarUsuario() }, modifier = Modifier.fillMaxWidth()) {
            Text("REGISTRARSE")
        }

        TextButton(onClick = { navController.popBackStack() }) {
            Text("Volver al Login")
        }
    }
}