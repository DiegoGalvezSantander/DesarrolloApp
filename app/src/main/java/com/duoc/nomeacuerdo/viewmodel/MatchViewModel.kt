package com.duoc.nomeacuerdo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.nomeacuerdo.data.model.ResenaRemota
import com.duoc.nomeacuerdo.repository.MatchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MatchViewModel(
    private val repository: MatchRepository
) : ViewModel() {

    private val _matches =
        MutableStateFlow<List<ResenaRemota>>(emptyList())

    val matches: StateFlow<List<ResenaRemota>> = _matches

    fun cargarMatches(correo: String) {
        viewModelScope.launch {
            try {
                _matches.value = repository.obtenerMatches(correo)
            } catch (e: Exception) {
                _matches.value = emptyList()
            }
        }
    }

    fun agregarMatch(
        nombreEvento: String,
        hora: String,
        direccion: String,
        correo: String
    ) {
        viewModelScope.launch {
            try {
                val nuevaResena = ResenaRemota(
                    nombre_evento = nombreEvento,
                    hora_evento = hora,
                    direccion_evento = direccion,
                    usuario_correo = correo
                )

                repository.registrarMatch(nuevaResena)

                cargarMatches(correo)

            } catch (e: Exception) {
            }
        }
    }
}
