package com.duoc.nomeacuerdo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.nomeacuerdo.data.model.ResenaRemota
import com.duoc.nomeacuerdo.data.model.UsuarioRemoto
import com.duoc.nomeacuerdo.repository.MatchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MatchViewModel(private val repository: MatchRepository) : ViewModel() {
    private val _matches = MutableStateFlow<List<ResenaRemota>>(emptyList())
    val matches: StateFlow<List<ResenaRemota>> = _matches

    private val _participantes = MutableStateFlow<List<UsuarioRemoto>>(emptyList())
    val participantes: StateFlow<List<UsuarioRemoto>> = _participantes

    private var ultimoEventoCargado: String? = null

    fun cargarMatches(correo: String, forzarRecarga: Boolean = false) {
        if (!forzarRecarga && _matches.value.isNotEmpty()) return

        viewModelScope.launch {
            try {
                _matches.value = emptyList()
                _matches.value = repository.obtenerMatches(correo)
            } catch (e: Exception) {
                _matches.value = emptyList()
            }
        }
    }

    fun cargarParticipantes(nombreEvento: String, forzarRecarga: Boolean = false) {
        if (!forzarRecarga && ultimoEventoCargado == nombreEvento && _participantes.value.isNotEmpty()) return

        ultimoEventoCargado = nombreEvento
        _participantes.value = emptyList()

        viewModelScope.launch {
            try {
                _participantes.value = repository.obtenerParticipantes(nombreEvento)
            } catch (e: Exception) {
                _participantes.value = emptyList()
            }
        }
    }

    fun agregarMatch(nombreEvento: String, hora: String, direccion: String, correo: String) {
        viewModelScope.launch {
            try {
                val nuevaResena = ResenaRemota(nombre_evento = nombreEvento, hora_evento = hora, direccion_evento = direccion, usuario_correo = correo)
                repository.registrarMatch(nuevaResena)
                cargarMatches(correo, true)
                cargarParticipantes(nombreEvento, true)
            } catch (e: Exception) {}
        }
    }

    fun borrarMatch(id: Int, correo: String, nombreEvento: String? = null) {
        viewModelScope.launch {
            try {
                repository.borrarMatch(id)
                cargarMatches(correo, true)
                if (nombreEvento != null) {
                    cargarParticipantes(nombreEvento, true)
                }
            } catch (e: Exception) {}
        }
    }
}