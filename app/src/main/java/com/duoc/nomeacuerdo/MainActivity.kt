package com.duoc.nomeacuerdo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duoc.nomeacuerdo.data.remote.MygosRetrofit
import com.duoc.nomeacuerdo.repository.MatchRepository
import com.duoc.nomeacuerdo.repository.SesionRepository
import com.duoc.nomeacuerdo.repository.UsuarioRepository
import com.duoc.nomeacuerdo.ui.DetalleEventoScreen
import com.duoc.nomeacuerdo.ui.EventosScreen
import com.duoc.nomeacuerdo.ui.LoginScreen
import com.duoc.nomeacuerdo.ui.MatchScreen
import com.duoc.nomeacuerdo.ui.PerfilScreen
import com.duoc.nomeacuerdo.ui.RegisterScreen
import com.duoc.nomeacuerdo.ui.theme.NomeacuerdoTheme
import com.duoc.nomeacuerdo.viewmodel.AuthViewModel
import com.duoc.nomeacuerdo.viewmodel.MatchViewModel

class MainActivity : ComponentActivity() {
    private val usuarioRepository by lazy { UsuarioRepository() }
    private val matchRepository by lazy { MatchRepository() }
    private val sesionRepository by lazy { SesionRepository(applicationContext) }
    private val authViewModel by lazy { AuthViewModel(usuarioRepository, sesionRepository) }
    private val matchViewModel by lazy { MatchViewModel(matchRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MygosRetrofit.init(applicationContext)
        enableEdgeToEdge()
        setContent {
            NomeacuerdoTheme {
                val navController = rememberNavController()
                val usuarioActual = authViewModel.usuarioLogueado.value
                val rutaInicio = if (usuarioActual != null) "home" else "login"
                NavHost(navController = navController, startDestination = rutaInicio) {
                    composable("login") { LoginScreen(navController, authViewModel) }
                    composable("register") { RegisterScreen(navController, authViewModel) }
                    composable("home") { EventosScreen(navController) }
                    composable("match") { MatchScreen(navController, matchViewModel, authViewModel) }
                    composable("perfil") { PerfilScreen(navController, authViewModel) }
                    composable("detalle_evento/{titulo}") { backStackEntry ->
                        val titulo = backStackEntry.arguments?.getString("titulo") ?: "Evento"
                        DetalleEventoScreen(navController, titulo, matchViewModel, authViewModel)
                    }
                }
            }
        }
    }
}