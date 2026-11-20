package com.duoc.nomeacuerdo.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.nomeacuerdo.data.model.UsuarioRemoto
import com.duoc.nomeacuerdo.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: UsuarioRepository, private val context: Context) : ViewModel() {

    private val prefs = context.getSharedPreferences("mygos_session", Context.MODE_PRIVATE)

    private val _loginCorreo = MutableStateFlow("")
    val loginCorreo: StateFlow<String> = _loginCorreo

    private val _loginPassword = MutableStateFlow("")
    val loginPassword: StateFlow<String> = _loginPassword

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError

    private val _usuarioLogueado = MutableStateFlow<UsuarioRemoto?>(null)
    val usuarioLogueado: StateFlow<UsuarioRemoto?> = _usuarioLogueado

    private val _regNombre = MutableStateFlow("")
    val regNombre: StateFlow<String> = _regNombre

    private val _regCorreo = MutableStateFlow("")
    val regCorreo: StateFlow<String> = _regCorreo

    private val _regPassword = MutableStateFlow("")
    val regPassword: StateFlow<String> = _regPassword

    private val _regError = MutableStateFlow<String?>(null)
    val regError: StateFlow<String?> = _regError

    private val _perfilGuardado = MutableStateFlow(false)
    val perfilGuardado: StateFlow<Boolean> = _perfilGuardado

    init {
        val correoGuardado = prefs.getString("correo_logueado", null)
        if (correoGuardado != null) {
            viewModelScope.launch {
                _usuarioLogueado.value = repository.obtenerUsuarioPorCorreo(correoGuardado)
            }
        }
    }

    fun onLoginCorreoChange(correo: String) { _loginCorreo.value = correo }
    fun onLoginPasswordChange(pass: String) { _loginPassword.value = pass }
    fun onRegNombreChange(nombre: String) { _regNombre.value = nombre }
    fun onRegCorreoChange(correo: String) { _regCorreo.value = correo }
    fun onRegPasswordChange(pass: String) { _regPassword.value = pass }

    fun hacerLogin() {
        val correo = _loginCorreo.value
        val pass = _loginPassword.value
        if (correo.isBlank() || pass.isBlank()) {
            _loginError.value = "Todos los campos son obligatorios"
            return
        }
        viewModelScope.launch {
            try {
                val usuario = repository.login(correo, pass)
                if (usuario != null) {
                    _usuarioLogueado.value = usuario
                    prefs.edit().putString("correo_logueado", usuario.correo).apply()
                    _loginError.value = null
                } else {
                    _loginError.value = "Correo o contraseña incorrectos"
                }
            } catch (e: Exception) {
                _loginError.value = "Error de red: ${e.message}"
            }
        }
    }

    fun registrarUsuario() {
        val nombre = _regNombre.value
        val correo = _regCorreo.value
        val pass = _regPassword.value
        if (nombre.isBlank() || correo.isBlank() || pass.isBlank()) {
            _regError.value = "Todos los campos son obligatorios"
            return
        }
        viewModelScope.launch {
            try {
                val existe = repository.obtenerUsuarioPorCorreo(correo)
                if (existe != null) {
                    _regError.value = "Este correo ya está registrado"
                    return@launch
                }
                val nuevoUsuario = UsuarioRemoto(nombre_completo = nombre, correo = correo, password = pass)
                val creado = repository.registrarUsuario(nuevoUsuario)
                _usuarioLogueado.value = creado
                prefs.edit().putString("correo_logueado", creado.correo).apply()
                _regError.value = null
            } catch (e: Exception) {
                _regError.value = "Error al registrar: ${e.message}"
            }
        }
    }

    fun actualizarDatosPerfil(nuevoNombre: String, nuevoCorreo: String, nuevaPassword: String) {
        val usuarioActual = _usuarioLogueado.value ?: return
        viewModelScope.launch {
            try {
                val passwordFinal = if (nuevaPassword.isBlank()) usuarioActual.password else nuevaPassword
                val usuarioModificado = usuarioActual.copy(nombre_completo = nuevoNombre, correo = nuevoCorreo, password = passwordFinal)
                val actualizado = repository.actualizarUsuario(usuarioModificado)
                _usuarioLogueado.value = actualizado
                prefs.edit().putString("correo_logueado", actualizado.correo).apply()
                _perfilGuardado.value = true
            } catch (e: Exception) {
                _perfilGuardado.value = false
            }
        }
    }

    fun actualizarFotoPerfil(uri: String) {
        val usuarioActual = _usuarioLogueado.value ?: return
        viewModelScope.launch {
            try {
                val usuarioModificado = usuarioActual.copy(foto_perfil_uri = uri)
                val actualizado = repository.actualizarUsuario(usuarioModificado)
                _usuarioLogueado.value = actualizado
            } catch (e: Exception) {}
        }
    }

    fun resetPerfilGuardado() { _perfilGuardado.value = false }

    fun cerrarSesion() {
        _usuarioLogueado.value = null
        prefs.edit().remove("correo_logueado").apply()
        _loginCorreo.value = ""
        _loginPassword.value = ""
    }
}