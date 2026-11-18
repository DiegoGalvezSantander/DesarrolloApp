package com.duoc.nomeacuerdo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duoc.nomeacuerdo.repository.UsuarioRepository
import com.duoc.nomeacuerdo.ui.*
import com.duoc.nomeacuerdo.ui.theme.NomeacuerdoTheme
import com.duoc.nomeacuerdo.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {

    private val usuarioRepository by lazy {
        UsuarioRepository()
    }

    private val authViewModel by lazy {
        AuthViewModel(usuarioRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            NomeacuerdoTheme {
                val navController = rememberNavController()
                val usuarioActual = authViewModel.usuarioLogueado.value
                val rutaInicio = if (usuarioActual != null) "home" else "login"

                NavHost(
                    navController = navController,
                    startDestination = rutaInicio
                ) {
                    composable("login") {
                        LoginScreen(navController, authViewModel)
                    }

                    composable("register") {
                        RegisterScreen(navController, authViewModel)
                    }

                    composable("home") {
                        EventosScreen(navController)
                    }

                    composable("match") {
                        MatchScreen(navController)
                    }

                    composable("perfil") {
                        PerfilScreen(navController, authViewModel)
                    }

                    composable("detalle_evento/{titulo}") { backStackEntry ->
                        val titulo = backStackEntry.arguments?.getString("titulo") ?: "Evento"
                        DetalleEventoScreen(navController, titulo)
                    }
                }
            }
        }
    }
}