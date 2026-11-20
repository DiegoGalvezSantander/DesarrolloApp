package com.duoc.nomeacuerdo.ui

import android.Manifest
import android.content.ActivityNotFoundException
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    val perfilGuardado by viewModel.perfilGuardado.collectAsState()

    var nombre by remember(usuario) { mutableStateOf(usuario?.nombre_completo ?: "") }
    var email by remember(usuario) { mutableStateOf(usuario?.correo ?: "") }
    var nuevaContrasena by remember { mutableStateOf("") }
    var uriTemporal by remember { mutableStateOf<Uri?>(null) }

    val uriParaMostrar = usuario?.foto_perfil_uri

    val takePictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && uriTemporal != null) {
            viewModel.actualizarFotoPerfil(uriTemporal.toString())
            Toast.makeText(context, "¡Foto guardada con éxito!", Toast.LENGTH_SHORT).show()
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            try {
                val archivoFoto = File(context.filesDir, "perfil_${System.currentTimeMillis()}.jpg")
                archivoFoto.createNewFile()
                val uri = FileProvider.getUriForFile(
                    context,
                    context.packageName + ".provider",
                    archivoFoto
                )
                uriTemporal = uri
                takePictureLauncher.launch(uri)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "No hay app de cámara", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
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

        Text(text = "Mi Perfil", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text(
            text = "Aquí puedes modificar tus datos personales y tu foto.",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable { cameraPermissionLauncher.launch(Manifest.permission.CAMERA) },
            contentAlignment = Alignment.Center
        ) {
            if (!uriParaMostrar.isNullOrBlank()) {
                AsyncImage(
                    model = uriParaMostrar,
                    contentDescription = "Foto",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text(
                    text = "Toca para\nañadir foto",
                    fontSize = 14.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        TextButton(
            onClick = { cameraPermissionLauncher.launch(Manifest.permission.CAMERA) }
        ) {
            Text("Cambiar Foto")
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre Completo") },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = nuevaContrasena,
            onValueChange = { nuevaContrasena = it },
            label = { Text("Nueva Contraseña (Opcional)") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            shape = MaterialTheme.shapes.medium
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { viewModel.actualizarDatosPerfil(nombre, email, nuevaContrasena) },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Guardar Cambios", fontSize = 16.sp)
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

    if (perfilGuardado) {
        AlertDialog(
            onDismissRequest = { viewModel.resetPerfilGuardado() },
            title = { Text("¡Guardado!") },
            text = { Text("Tus datos se han actualizado correctamente en la nube.") },
            confirmButton = {
                Button(onClick = {
                    viewModel.resetPerfilGuardado()
                    nuevaContrasena = ""
                }) {
                    Text("OK")
                }
            }
        )
    }
}