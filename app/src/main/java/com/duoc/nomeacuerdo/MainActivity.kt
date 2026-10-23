package com.duoc.nomeacuerdo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duoc.nomeacuerdo.ui.DetalleEventoScreen
import com.duoc.nomeacuerdo.ui.EventosScreen
import com.duoc.nomeacuerdo.ui.LoginScreen
import com.duoc.nomeacuerdo.ui.RegisterScreen
import com.duoc.nomeacuerdo.ui.theme.NomeacuerdoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NomeacuerdoTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") { LoginScreen(navController) }
                    composable("register") { RegisterScreen(navController) }
                    composable("home") { EventosScreen(navController) }
                    composable("detalle_evento/{titulo}") { backStackEntry ->
                        val titulo = backStackEntry.arguments?.getString("titulo") ?: "Evento"
                        DetalleEventoScreen(navController, titulo)
                    }
                }
            }
        }
    }
}