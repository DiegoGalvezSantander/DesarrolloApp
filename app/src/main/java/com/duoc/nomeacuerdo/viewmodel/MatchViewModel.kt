package com.duoc.nomeacuerdo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.nomeacuerdo.model.Match
import com.duoc.nomeacuerdo.repository.MatchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MatchViewModel(private val repository: MatchRepository) : ViewModel() {
    private val _matches = MutableStateFlow<List<Match>>(emptyList())
    val matches: StateFlow<List<Match>> = _matches

    fun cargarMatches(correo: String) {
        viewModelScope.launch {
            _matches.value = repository.obtenerMatches(correo)
        }
    }

    fun agregarMatch(nombreEvento: String, hora: String, direccion: String, correo: String) {
        viewModelScope.launch {
            val nuevoMatch = Match(
                nombreEvento = nombreEvento,
                horaEvento = hora,
                direccionEvento = direccion,
                usuarioCorreo = correo
            )
            repository.registrarMatch(nuevoMatch)
            cargarMatches(correo)
        }
    }
}