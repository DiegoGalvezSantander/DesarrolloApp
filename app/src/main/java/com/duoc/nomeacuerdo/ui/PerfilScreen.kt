package com.duoc.nomeacuerdo.ui

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.duoc.nomeacuerdo.viewmodel.AuthViewModel
import java.io.File

@Composable
fun PerfilScreen(navController: NavController, viewModel: AuthViewModel) {
    val context = LocalContext.current
    val usuario by viewModel.usuarioLogueado.collectAsState()

    var nombre by remember { mutableStateOf(usuario?.nombreCompleto ?: "") }
    var email by remember { mutableStateOf(usuario?.correo ?: "") }
    var pin by remember { mutableStateOf("") }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var mostrarMensajeGuardado by remember { mutableStateOf(false) }

    val takePictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && imageUri != null) {
            val bmp = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
            bitmap = bmp
            viewModel.actualizarFotoPerfil(imageUri.toString())
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val file = File.createTempFile("profile_photo", ".jpg", context.cacheDir)
            file.deleteOnExit()
            val uri = FileProvider.getUriForFile(context, context.packageName + ".provider", file)
            imageUri = uri
            takePictureLauncher.launch(uri)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text("< Volver")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Mi Perfil", fontSize = 28.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 24.dp))

        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable { cameraPermissionLauncher.launch(Manifest.permission.CAMERA) },
            contentAlignment = Alignment.Center
        ) {
            if (bitmap != null) {
                Image(bitmap = bitmap!!.asImageBitmap(), contentDescription = "Foto", contentScale = ContentScale.Crop)
            } else if (usuario?.fotoPerfilUri != null) {
                AsyncImage(model = usuario?.fotoPerfilUri, contentDescription = "Foto", contentScale = ContentScale.Crop)
            } else {
                Text(text = "FOTO", fontSize = 18.sp)
            }
        }

        TextButton(onClick = { cameraPermissionLauncher.launch(Manifest.permission.CAMERA) }) {
            Text("⚙ Editar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = pin,
            onValueChange = { pin = it },
            label = { Text("Nueva Contraseña / PIN") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                viewModel.actualizarDatosPerfil(nombre, email, pin)
                mostrarMensajeGuardado = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Cambios")
        }

        TextButton(
            onClick = {
                viewModel.cerrarSesion()
                navController.navigate("login") { popUpTo("home") { inclusive = true } }
            }
        ) {
            Text("Cerrar Sesión", color = MaterialTheme.colorScheme.error)
        }
    }

    if (mostrarMensajeGuardado) {
        AlertDialog(
            onDismissRequest = { mostrarMensajeGuardado = false },
            title = { Text("¡Guardado!") },
            text = { Text("Tus datos se han actualizado correctamente.") },
            confirmButton = { Button(onClick = { mostrarMensajeGuardado = false }) { Text("OK") } }
        )
    }
}