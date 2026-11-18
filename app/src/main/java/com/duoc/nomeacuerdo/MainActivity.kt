package com.duoc.nomeacuerdo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duoc.nomeacuerdo.repository.UsuarioRepository
import com.duoc.nomeacuerdo.ui.LoginScreen
import com.duoc.nomeacuerdo.ui.PerfilScreen
import com.duoc.nomeacuerdo.ui.RegisterScreen
import com.duoc.nomeacuerdo.ui.theme.NomeacuerdoTheme
import com.duoc.nomeacuerdo.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    private val usuarioRepository by lazy { UsuarioRepository() }
    private val authViewModel by lazy { AuthViewModel(usuarioRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NomeacuerdoTheme {
                val navController = rememberNavController()
                val usuarioActual = authViewModel.usuarioLogueado.value
                val rutaInicio = if (usuarioActual != null) "home" else "login"

                NavHost(navController = navController, startDestination = rutaInicio) {
                    composable("login") { LoginScreen(navController, authViewModel) }
                    composable("register") { RegisterScreen(navController, authViewModel) }
                    composable("home") {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Home en construcción")
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { navController.navigate("perfil") }) {
                                Text("Ir a mi Perfil")
                            }
                        }
                    }
                    composable("perfil") { PerfilScreen(navController, authViewModel) }
                }
            }
        }
    }
}