package com.duoc.nomeacuerdo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.nomeacuerdo.data.model.UsuarioRemoto
import com.duoc.nomeacuerdo.repository.SesionRepository
import com.duoc.nomeacuerdo.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: UsuarioRepository,
    private val sesionRepository: SesionRepository
) : ViewModel() {

    private val _loginCorreo = MutableStateFlow("")
    val loginCorreo: StateFlow<String> = _loginCorreo

    private val _loginPassword = MutableStateFlow("")
    val loginPassword: StateFlow<String> = _loginPassword

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError

    private val _usuarioLogueado = MutableStateFlow<UsuarioRemoto?>(null)
    val usuarioLogueado: StateFlow<UsuarioRemoto?> = _usuarioLogueado

    private val _registroNombre = MutableStateFlow("")
    val registroNombre: StateFlow<String> = _registroNombre

    private val _registroCorreo = MutableStateFlow("")
    val registroCorreo: StateFlow<String> = _registroCorreo

    private val _registroPassword = MutableStateFlow("")
    val registroPassword: StateFlow<String> = _registroPassword

    private val _registroError = MutableStateFlow<String?>(null)
    val registroError: StateFlow<String?> = _registroError

    private val _perfilGuardado = MutableStateFlow(false)
    val perfilGuardado: StateFlow<Boolean> = _perfilGuardado

    init {
        val correoGuardado = sesionRepository.obtenerCorreoSesion()
        if (correoGuardado != null) {
            viewModelScope.launch {
                _usuarioLogueado.value = repository.obtenerUsuarioPorCorreo(correoGuardado)
            }
        }
    }

    fun onLoginCorreoChange(correo: String) {
        _loginCorreo.value = correo
    }

    fun onLoginPasswordChange(password: String) {
        _loginPassword.value = password
    }

    fun onRegistroNombreChange(nombre: String) {
        _registroNombre.value = nombre
    }

    fun onRegistroCorreoChange(correo: String) {
        _registroCorreo.value = correo
    }

    fun onRegistroPasswordChange(password: String) {
        _registroPassword.value = password
    }

    fun hacerLogin() {
        val correo = _loginCorreo.value
        val password = _loginPassword.value
        if (correo.isBlank() || password.isBlank()) {
            _loginError.value = "Todos los campos son obligatorios"
            return
        }
        viewModelScope.launch {
            try {
                val usuario = repository.login(correo, password)
                if (usuario != null) {
                    _usuarioLogueado.value = usuario
                    sesionRepository.guardarCorreoSesion(usuario.correo)
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
        val nombre = _registroNombre.value
        val correo = _registroCorreo.value
        val password = _registroPassword.value
        if (nombre.isBlank() || correo.isBlank() || password.isBlank()) {
            _registroError.value = "Todos los campos son obligatorios"
            return
        }
        viewModelScope.launch {
            try {
                val existe = repository.obtenerUsuarioPorCorreo(correo)
                if (existe != null) {
                    _registroError.value = "Este correo ya está registrado"
                    return@launch
                }
                val nuevoUsuario = UsuarioRemoto(
                    nombre_completo = nombre,
                    correo = correo,
                    password = password
                )
                val creado = repository.registrarUsuario(nuevoUsuario)
                _usuarioLogueado.value = creado
                sesionRepository.guardarCorreoSesion(creado.correo)
                _registroError.value = null
            } catch (e: Exception) {
                _registroError.value = "Error al registrar: ${e.message}"
            }
        }
    }

    fun actualizarDatosPerfil(
        nuevoNombre: String,
        nuevoCorreo: String,
        nuevaPassword: String
    ) {
        val usuarioActual = _usuarioLogueado.value ?: return
        viewModelScope.launch {
            try {
                val passwordFinal =
                    if (nuevaPassword.isBlank()) usuarioActual.password else nuevaPassword
                val usuarioModificado = usuarioActual.copy(
                    nombre_completo = nuevoNombre,
                    correo = nuevoCorreo,
                    password = passwordFinal
                )
                val actualizado = repository.actualizarUsuario(usuarioModificado)
                _usuarioLogueado.value = actualizado
                sesionRepository.guardarCorreoSesion(actualizado.correo)
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
            } catch (e: Exception) {
            }
        }
    }

    fun resetPerfilGuardado() {
        _perfilGuardado.value = false
    }

    fun cerrarSesion() {
        _usuarioLogueado.value = null
        sesionRepository.borrarSesion()
        _loginCorreo.value = ""
        _loginPassword.value = ""
    }
}