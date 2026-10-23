package com.duoc.nomeacuerdo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room3.Room
import com.duoc.nomeacuerdo.model.AppDatabase
import com.duoc.nomeacuerdo.repository.MatchRepository
import com.duoc.nomeacuerdo.ui.DetalleEventoScreen
import com.duoc.nomeacuerdo.ui.EventosScreen
import com.duoc.nomeacuerdo.ui.LoginScreen
import com.duoc.nomeacuerdo.ui.MatchScreen
import com.duoc.nomeacuerdo.ui.RegisterScreen
import com.duoc.nomeacuerdo.ui.theme.NomeacuerdoTheme
import com.duoc.nomeacuerdo.viewmodel.MatchViewModel

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "mygos_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    private val matchRepository by lazy { MatchRepository(db.matchDao()) }
    private val matchViewModel by lazy { MatchViewModel(matchRepository) }

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
                    composable("match") { MatchScreen(navController, matchViewModel) }
                    composable("detalle_evento/{titulo}") { backStackEntry ->
                        val titulo = backStackEntry.arguments?.getString("titulo") ?: "Evento"
                        DetalleEventoScreen(navController, titulo, matchViewModel)
                    }
                }
            }
        }
    }
}