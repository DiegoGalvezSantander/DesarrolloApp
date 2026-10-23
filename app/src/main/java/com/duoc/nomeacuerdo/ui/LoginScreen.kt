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
fun LoginScreen(navController: NavController, viewModel: AuthViewModel) {
    val correo by viewModel.loginCorreo.collectAsState()
    val password by viewModel.loginPassword.collectAsState()
    val error by viewModel.loginError.collectAsState()
    val usuarioLogueado by viewModel.usuarioLogueado.collectAsState()

    var mostrarModal by remember { mutableStateOf(false) }

    LaunchedEffect(usuarioLogueado) {
        if (usuarioLogueado != null) {
            mostrarModal = true
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "LOGO", fontSize = 40.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "LOGIN", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { viewModel.onLoginCorreoChange(it) },
            label = { Text("CORREO") },
            modifier = Modifier.fillMaxWidth(),
            isError = error != null
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.onLoginPasswordChange(it) },
            label = { Text("CONTRASEÑA") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            isError = error != null,
            supportingText = { if (error != null) Text(text = error!!, color = MaterialTheme.colorScheme.error) }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { viewModel.hacerLogin() }, modifier = Modifier.fillMaxWidth()) {
            Text("INICIAR SESIÓN")
        }

        TextButton(onClick = { navController.navigate("register") }) {
            Text("REGISTRARTE")
        }
    }

    if (mostrarModal) {
        AlertDialog(
            onDismissRequest = { mostrarModal = false },
            title = { Text("Confirmación") },
            text = { Text("Has iniciado sesión correctamente.") },
            confirmButton = {
                Button(onClick = {
                    mostrarModal = false
                    navController.navigate("home") { popUpTo("login") { inclusive = true } }
                }) {
                    Text("OK")
                }
            }
        )
    }
}