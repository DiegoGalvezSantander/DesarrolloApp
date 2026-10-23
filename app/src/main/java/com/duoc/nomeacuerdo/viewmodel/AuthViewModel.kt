package com.duoc.nomeacuerdo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.nomeacuerdo.model.Usuario
import com.duoc.nomeacuerdo.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: UsuarioRepository) : ViewModel() {

    private val _loginCorreo = MutableStateFlow("")
    val loginCorreo: StateFlow<String> = _loginCorreo
    private val _loginPassword = MutableStateFlow("")
    val loginPassword: StateFlow<String> = _loginPassword
    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError
    private val _usuarioLogueado = MutableStateFlow<Usuario?>(null)
    val usuarioLogueado: StateFlow<Usuario?> = _usuarioLogueado

    private val _regNombre = MutableStateFlow("")
    val regNombre: StateFlow<String> = _regNombre
    private val _regCorreo = MutableStateFlow("")
    val regCorreo: StateFlow<String> = _regCorreo
    private val _regPassword = MutableStateFlow("")
    val regPassword: StateFlow<String> = _regPassword
    private val _regError = MutableStateFlow<String?>(null)
    val regError: StateFlow<String?> = _regError

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
            val usuario = repository.login(correo, pass)
            if (usuario != null) {
                _usuarioLogueado.value = usuario
                _loginError.value = null
            } else {
                _loginError.value = "Correo o contraseña incorrectos"
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
        if (!correo.matches(Regex("^[\\w.-]+@[\\w.-]+\\.\\w+$"))) {
            _regError.value = "El formato del correo no es válido"
            return
        }
        if (pass.length < 6) {
            _regError.value = "La contraseña debe tener al menos 6 caracteres"
            return
        }

        viewModelScope.launch {
            val existe = repository.obtenerUsuarioPorCorreo(correo)
            if (existe != null) {
                _regError.value = "Este correo ya está registrado"
                return@launch
            }
            val nuevoUsuario = Usuario(nombreCompleto = nombre, correo = correo, password = pass)
            repository.registrarUsuario(nuevoUsuario)
            _usuarioLogueado.value = nuevoUsuario
            _regError.value = null
        }
    }

    fun actualizarFotoPerfil(uri: String) {
        val usuarioActual = _usuarioLogueado.value ?: return
        viewModelScope.launch {
            val usuarioModificado = usuarioActual.copy(fotoPerfilUri = uri)
            repository.actualizarUsuario(usuarioModificado)
            _usuarioLogueado.value = usuarioModificado
        }
    }

    fun actualizarDatosPerfil(nuevoNombre: String, nuevoCorreo: String) {
        val usuarioActual = _usuarioLogueado.value ?: return
        viewModelScope.launch {
            val usuarioModificado = usuarioActual.copy(nombreCompleto = nuevoNombre, correo = nuevoCorreo)
            repository.actualizarUsuario(usuarioModificado)
            _usuarioLogueado.value = usuarioModificado
        }
    }

    fun cerrarSesion() {
        _usuarioLogueado.value = null
        _loginCorreo.value = ""
        _loginPassword.value = ""
    }
}