package com.duoc.nomeacuerdo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import com.duoc.nomeacuerdo.ui.theme.NomeacuerdoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NomeacuerdoTheme {
                Text("Proyecto Mygos iniciado")
            }
        }
    }
}