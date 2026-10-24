package com.duoc.nomeacuerdo.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.net.Uri
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.duoc.nomeacuerdo.repository.EventoRepository
import com.google.android.gms.location.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventosScreen(navController: NavController) {
    val context = LocalContext.current
    val listaEventos = EventoRepository.listaEventos
    var busqueda by remember { mutableStateOf("") }

    var location by remember { mutableStateOf<Location?>(null) }
    var permisoConcedido by remember { mutableStateOf(false) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted -> permisoConcedido = granted }

    LaunchedEffect(permisoConcedido) {
        if (permisoConcedido) {
            obtenerUbicacion(context) { loc -> location = loc }
        }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(16.dp)
            ) {
                Text(text = "Eventos", fontSize = 28.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
                OutlinedTextField(
                    value = busqueda,
                    onValueChange = { busqueda = it },
                    label = { Text("Buscar Eventos") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextButton(onClick = { locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION) }) {
                    Icon(Icons.Default.LocationOn, contentDescription = "Ubicacion")
                    Spacer(modifier = Modifier.width(4.dp))
                    if (location != null) {
                        Text("Lat: ${location!!.latitude.toInt()}, Lon: ${location!!.longitude.toInt()}")
                    } else {
                        Text("Obtener mi ubicación")
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(icon = { Icon(Icons.Default.Home, null) }, label = { Text("Inicio") }, selected = true, onClick = { })
                NavigationBarItem(icon = { Icon(Icons.Default.Star, null) }, label = { Text("Match") }, selected = false, onClick = { navController.navigate("match") { popUpTo("home") { inclusive = true } } })
                NavigationBarItem(icon = { Icon(Icons.Default.Person, null) }, label = { Text("Perfil") }, selected = false, onClick = { navController.navigate("perfil") })
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(listaEventos) { evento ->
                Card(
                    modifier = Modifier.fillMaxWidth().clickable { navController.navigate("detalle_evento/${Uri.encode(evento.titulo)}") },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = evento.titulo, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(text = evento.hora, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@SuppressLint("MissingPermission")
fun obtenerUbicacion(context: Context, onLocation: (Location?) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()
    fusedLocationClient.requestLocationUpdates(
        locationRequest,
        object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                onLocation(result.lastLocation)
                fusedLocationClient.removeLocationUpdates(this)
            }
        },
        Looper.getMainLooper()
    )
}